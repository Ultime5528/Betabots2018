/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Waypoint;

public class AutoPlateformeDroiteA extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutoPlateformeDroiteA() {
   
    addSequential(new SuivreTrajectoire(new Waypoint[] {
				new Waypoint(0, 0, 0),
				new Waypoint(2.54, 1.1, 0),
				new Waypoint(3.0, 1.1, 0)
    }, 0.42, -0.2), 4);

    
  }
}
