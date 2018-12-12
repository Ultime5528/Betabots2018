package frc.robot.commands.autonomes;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.K;
import frc.robot.commands.BougerTreuil;
import frc.robot.commands.DescendreTreuil;
import frc.robot.commands.FermerPorte;
import frc.robot.commands.MaintienTreuil;
import frc.robot.commands.OuvrirPorte;
import frc.robot.commands.PrendreBalles;
import frc.robot.commands.StopIntake;
import frc.robot.commands.SuivreTrajectoire;
import frc.robot.commands.ViserAvancer;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

/**
 * Autonome
 */
public class AutonomeDescendre extends CommandGroup{

    public AutonomeDescendre(){
            

        // Descendre de la plateforme
        addParallel(new OuvrirPorte());
        
        addSequential(new SuivreTrajectoire(new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(1.2, 0, 0)
        }, 0.5, -0.7), 4);
        
        addSequential(new BougerTreuil(K.Intake.TREUIL_HAUTEUR_CAROTTE));
        addSequential(new MaintienTreuil());
        /*
        addSequential(new BougerTreuil(K.Intake.TREUIL_HAUTEUR_CAROTTE), 3);
        addParallel(new MaintienTreuil());
        
        // Autre trajectoire, qui dépend de la position des blocs
        //addSequential(trajectoireArche, 1);
        
        
        
        
        //addSequential(new FermerPorte());
        addParallel(new PrendreBalles());

        addSequential(new SuivreTrajectoire(new Waypoint[] {
        new Waypoint(0, 0, 0),
        new Waypoint(1.1, 0, 0)
        }, 0.5, -0.25), 3);

        addSequential(new BougerTreuil(K.Intake.TREUIL_HAUTEUR_BALLE), 1);

        addSequential(new SuivreTrajectoire(new Waypoint[] {
        new Waypoint(0, 0, Pathfinder.d2r(-180)),
        new Waypoint(-1.1, 0, Pathfinder.d2r(-180))
        }, -0.5, 0.5), 3);

        addSequential(new TimedCommand(2));

        addSequential(new StopIntake());
        */
    }
}