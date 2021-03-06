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

public class ViserAvancer extends Command {

  private double centreX;
  private double largeurErreur;

  public ViserAvancer() {
    requires(Robot.basePilotable);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    centreX = 0.0;
    largeurErreur = 0.0;

    Robot.camera.startCamera();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    /**
     * Centre - Turn
     */

    centreX = Robot.camera.getCenterX();

    double turn = 0.0;

    // Si on est trop loin du centre
    if (Math.abs(centreX) > K.Camera.X_THRESHOLD) {

      // Gauche ou droite, selon le signe de l'erreur.
      turn = Math.signum(centreX) * K.Camera.TURN_SPEED;

    }

    /**
     * Largeur - Forward
     */

    double largeur = Robot.camera.getLargeur();

    // La différence avec la largeur voulue
    largeurErreur = K.Camera.LARGEUR_TARGET - largeur;

    double forward = 0.0;

    // Si on est trop loin de la cible
    if (Math.abs(largeurErreur) > K.Camera.LARGEUR_THRESHOLD) {

      // Avant ou arrière, selon le signe de l'erreur
      forward = Math.signum(largeurErreur) * K.Camera.FORWARD_SPEED;

    }

    // On envoie les valeurs aux moteurs
    Robot.basePilotable.arcadeDrive(forward, turn);

  }

  
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {

    // La cible est atteinte lorsque la caméra est centrée et à la bonne distance.
    return Math.abs(centreX) < K.Camera.X_THRESHOLD && Math.abs(largeurErreur) < K.Camera.LARGEUR_THRESHOLD;

  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.basePilotable.stop();
    Robot.camera.stopCamera();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}