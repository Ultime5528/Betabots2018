/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.K;
import frc.robot.commands.MaintienTreuil;

public class Treuil extends Subsystem {

  private VictorSP moteurTreuil;
  private AnalogPotentiometer pot;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new MaintienTreuil());
  }

  public Treuil() {
    moteurTreuil = new VictorSP(K.Ports.INTAKE_MOTEUR_TREUIL);
    addChild("Moteur Treuil", moteurTreuil);

    pot = new AnalogPotentiometer(K.Ports.INTAKE_POTENTIOMETRE);
    addChild("Potentiometre", pot);

  }

  public double getPot() {
    return pot.get();

  }

  public void descendreTreuil() {

    if (K.Intake.TREUIL_POT_MIN > getPot()) {
      moteurTreuil.set(0.0);
    } else {
      moteurTreuil.set(K.Intake.VITESSE_TREUIL_DESCENDRE);

    }
  }

  public void monterTreuil() {

    if (K.Intake.TREUIL_POT_MAX < getPot()) {
      moteurTreuil.set(0.0);
    } else {
      moteurTreuil.set(K.Intake.VITESSE_TREUIL_MONTER);

    }

  }

  public void maintienTreuil() {
    if (getPot() > K.Intake.TREUIL_POT_MIN) {

      moteurTreuil.set(K.Intake.VITESSE_TREUIL_MAINTIEN);
    } else {
      moteurTreuil.set(0.0);
    }

  }

  public void stopTreuil() {

    moteurTreuil.set(0.0);

  }

}