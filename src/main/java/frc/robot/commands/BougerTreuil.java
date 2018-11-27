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

public class BougerTreuil extends Command {

  private double hauteur;

  public BougerTreuil(double h) {
    hauteur = h;
    requires(Robot.treuil);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if (hauteur > Robot.treuil.getPot()) {
      Robot.treuil.monterTreuil();
    } else if (hauteur < Robot.treuil.getPot()) {
      Robot.treuil.descendreTreuil();
    }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(hauteur - Robot.treuil.getPot()) < K.Intake.TREUIL_TRESHOLD;
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
