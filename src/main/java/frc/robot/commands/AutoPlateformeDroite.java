/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.Intake;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class AutoPlateformeDroite extends CommandGroup {
  /**
   * Add your docs here.
*/

  public AutoPlateformeDroite() {

    addSequential(new SuivreTrajectoire(new Waypoint[] {
      new Waypoint(0, 0, Pathfinder.d2r(0)),
      new Waypoint(-1.5, 1.5, Pathfinder.d2r(45))
      }, 0.55, -0.1));

    addParallel(new PrendreBalles());

  

  }

}

 
 