package frc;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Stack;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;

public class Properties {

    private Class<?> propertyClass;

    private NetworkTableInstance ntInst;

    private Stack<Change> changes;
    private Object lock;

    private ArrayList<Entry> entries;

    public Properties(Class<?> propertyClass) {

        this.propertyClass = propertyClass;

        changes = new Stack<>();
        lock = new Object();

        entries = new ArrayList<>();

        ntInst = NetworkTableInstance.getDefault();

        addTable(propertyClass, ntInst.getTable("Properties"));

    }

    public void performChanges() {

        synchronized(lock) {

            while(!changes.isEmpty()) {

                Change change = changes.pop();
                change.entry.performChange(change.notif);

            }

        }

    }

    // Recursive
    private void addTable(Class<?> root, NetworkTable table) {
        System.out.println("Before addFields of" + root.getSimpleName());
        addFields(root, table);

        Class<?>[] innerClasses = root.getClasses();

        for(Class<?> theClass : innerClasses) {
            System.out.println(theClass.getSimpleName());
            if (Modifier.isStatic(theClass.getModifiers()))
                addTable(theClass, table.getSubTable(theClass.getSimpleName()));
        }
            

        /*
        for (Class<?> theClass : innerClasses)
            if (Modifier.isStatic(theClass.getModifiers()))
                addTable(theClass, table.getSubTable(theClass.getSimpleName()));
        */
    }

    private void addFields(Class<?> theClass, NetworkTable table) {

        for (Field f : theClass.getFields())
            if (!Modifier.isFinal(f.getModifiers()) && Modifier.isStatic(f.getModifiers())
                    && (f.getType() == double.class)) { // Seulement les double sont supportés pour le moment

                entries.add(new Entry(f, table));

            }

    }

    private class Entry {

        NetworkTableEntry entry;
        Field field;
        Class<?> type;

        public Entry(Field field, NetworkTable table) {

            this.field = field;
            entry = table.getEntry(field.getName());

            type = field.getType();

            try {

                if (type == double.class) {
                    entry.setDouble(field.getDouble(null));
                }

            } catch (IllegalAccessException e) {
                DriverStation.reportError("Erreur de réflection dans Properties", e.getStackTrace());
            }

            entry.addListener(this::handleNotification, EntryListenerFlags.kUpdate);

        }

        private void handleNotification(EntryNotification notif) {

            synchronized (lock) {
                changes.add(new Change(this, notif));
            }

        }

        public void performChange(EntryNotification notif) {

            try {

                if (type == double.class) {
                    field.set(null, notif.value.getDouble());
                }

                DriverStation.reportWarning(field.getName() + " new value : " + field.getDouble(null), false);

            } catch (IllegalAccessException e) {
                DriverStation.reportError("Erreur de réflection dans Properties", e.getStackTrace());
            }

        }

    }

    private class Change {

        public EntryNotification notif;
        public Entry entry;

        public Change(Entry entry, EntryNotification notif) {
            this.entry = entry;
            this.notif = notif;
        }

    }

}