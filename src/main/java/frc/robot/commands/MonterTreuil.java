/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.K;
import frc.robot.Robot;

public class MonterTreuil extends Command {
  public MonterTreuil() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.treuil);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    Robot.treuil.monterTreuil();

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.treuil.getPot() >= K.Intake.TREUIL_POT_MAX;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.treuil.stopTreuil();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
