/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.K;

public class Intake extends Subsystem {

  private VictorSP moteurTreuil;
  private VictorSP moteurIntake;
  private AnalogPotentiometer pot;
  private VictorSP moteurPorte;

  @Override
  public void initDefaultCommand() {

  }

  public Intake() {

    moteurIntake = new VictorSP(K.Ports.INTAKE_MOTEUR);
    addChild("Moteur Intake", moteurIntake);

    moteurTreuil = new VictorSP(K.Ports.INTAKE_MOTEUR_TRAPE);
    addChild("Moteur Treuil", moteurTreuil);

    pot = new AnalogPotentiometer(K.Ports.INTAKE_POTENTIOMETRE);
    addChild("Potentiometre", pot);

    moteurPorte = new VictorSP(K.Ports.PORTE_MOTEUR);
    addChild("Moteur Porte", moteurPorte);

  }

  public void gober() {

    moteurIntake.set(K.Intake.VITESSE_GOBER);

  }

  public void recracher() {

    moteurIntake.set(K.Intake.VITESSE_CRACHER);

  }

  public double getPot() {
    return pot.get();
    

  }

  public void monterTreuil() {

    moteurIntake.set(K.Intake.VITESSE_TREUIL_MONTER);

  }

  public void descendreTreuil() {

    moteurIntake.set(K.Intake.VITESSE_TREUIL_DESCENDRE);

  }

  public void stopTreuil() {

    moteurTreuil.set(0.0);

  }

  public void stopConvoyeur() {

    moteurIntake.set(0.0);

  }

  public void stopPorte() {

    moteurIntake.set(0.0);

  }

}