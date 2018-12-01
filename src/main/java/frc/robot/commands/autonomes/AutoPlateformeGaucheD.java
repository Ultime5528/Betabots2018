/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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

public class AutoPlateformeGaucheD extends Autonome {
  /**
   * Add your docs here.
   */
  public AutoPlateformeGaucheD() {
    
    super(new SuivreTrajectoire(new Waypoint[] {
        new Waypoint(0, 0, Pathfinder.d2r(0)),
        new Waypoint(2, 0, Pathfinder.d2r(0)),
        new Waypoint(3.0, -0.1, Pathfinder.d2r(-5)),
        //new Waypoint(2.3, -2.05, Pathfinder.d2r(-25))
      }, 0.5, -0.7),

      new SuivreTrajectoire(new Waypoint[] {
        new Waypoint(0, 0, 0),
        new Waypoint(0, 0, 0)
      }, 0, 0));

  }
}

