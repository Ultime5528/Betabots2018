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
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import frc.robot.K;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {

  private VictorSP moteurTreuil;
  private VictorSP moteurIntake;
  private AnalogPotentiometer pot;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public Intake() {

    moteurIntake = new VictorSP(K.Ports.INTAKE_MOTEUR);
    addChild("Moteur Intake", moteurIntake);

    moteurTreuil = new VictorSP(K.Ports.INTAKE_MOTEUR_TRAPE);
    addChild("Moteur Trape", moteurTreuil);

    pot = new AnalogPotentiometer(K.Ports.INTAKE_POTENTIOMETRE);
    addChild("Potentiometre", pot);

  }

}