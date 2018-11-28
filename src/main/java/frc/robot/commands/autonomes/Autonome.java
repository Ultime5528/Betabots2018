package frc.robot.commands.autonomes;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.K;
import frc.robot.commands.BougerTreuil;
import frc.robot.commands.FermerPorte;
import frc.robot.commands.PrendreBalles;
import frc.robot.commands.SuivreTrajectoire;
import frc.robot.commands.ViserAvancer;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

/**
 * Autonome
 */
public class Autonome extends CommandGroup{

    public Autonome(SuivreTrajectoire trajectoireDescente, SuivreTrajectoire trajectoireArche){
            

        // Descendre de la plateforme
        addParallel(new FermerPorte());

        addSequential(trajectoireDescente, 4);

        
        addParallel(new BougerTreuil(K.Intake.TREUIL_HAUTEUR_CAROTTE), 3);

        // Autre trajectoire, qui dépend de la position des blocs
        addSequential(trajectoireArche, 1);

        addSequential(new ViserAvancer(), 3);

        addParallel(new PrendreBalles());

        addSequential(new SuivreTrajectoire(new Waypoint[] {
        new Waypoint(0, 0, 0),
        new Waypoint(0.3, 0, 0)
        }, 0.25, -0.25), 1);

        addSequential(new BougerTreuil(K.Intake.TREUIL_HAUTEUR_BALLE), 1);

        addSequential(new SuivreTrajectoire(new Waypoint[] {
        new Waypoint(0, 0, Pathfinder.d2r(-180)),
        new Waypoint(-0.3, 0, Pathfinder.d2r(-180))
        }, -0.25, 0.25), 1);
    }
}