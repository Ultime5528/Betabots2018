/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.K;

public class Porte extends Subsystem {
  
  private VictorSP moteurPorte;

  @Override
  public void initDefaultCommand() {

  }

  public Porte() {

    moteurPorte = new VictorSP(K.Ports.PORTE_MOTEUR);
    addChild("Moteur porte", moteurPorte);

  }

  public void ouvrirPorte() {
    moteurPorte.set(K.Porte.VITESSE_PORTE_OUVRIR);

  }
  public void fermerPorte() {
    moteurPorte.set(K.Porte.VITESSE_PORTE_FERMER);

  }
  public void stopPorte() {
    moteurPorte.set(0.0);

  }

}
