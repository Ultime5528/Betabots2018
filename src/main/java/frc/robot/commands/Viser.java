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

public class Viser extends Command {

  private double centreX;

  public Viser() {
    requires(Robot.basePilotable);
  }
  
  @Override
  protected void initialize() {
    centreX = 0.0;
  }

  @Override
  protected void execute() {

    centreX = Robot.camera.getCenterX();

    double turn = Math.signum(centreX) * K.Camera.TURN_SPEED;

    Robot.basePilotable.arcadeDrive(0, turn);;

  }

  @Override
  protected boolean isFinished() {
    return Math.abs(centreX) < K.Camera.X_THRESHOLD;
  }

  @Override
  protected void end() {
    Robot.basePilotable.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }
}