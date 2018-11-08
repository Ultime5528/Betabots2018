/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.K;
import frc.robot.commands.Piloter;

public class BasePilotable extends Subsystem {

  private VictorSP moteurDroit;
  private VictorSP moteurGauche;
  private DifferentialDrive drive;

  public BasePilotable() {

    moteurDroit = new VictorSP(K.Ports.BASE_PILOTABLE_MOTEUR_DROIT);
    addChild("Moteur Droit", moteurDroit);

    moteurGauche = new VictorSP(K.Ports.BASE_PILOTABLE_MOTEUR_GAUCHE);
    addChild("Moteur Gauche", moteurGauche);

    drive = new DifferentialDrive(moteurGauche, moteurDroit);
    addChild("Drive", drive);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Piloter());
  }

  public void arcadeDrive(Joystick joystick) {

    drive.arcadeDrive(joystick.getY(), joystick.getX());

  }

  public void arcadeDrive(double forward, double turn) {

    drive.arcadeDrive(-forward, turn);

  }

  public void stop(){
    moteurDroit.set(0);
    moteurGauche.set(0);
  }

}
