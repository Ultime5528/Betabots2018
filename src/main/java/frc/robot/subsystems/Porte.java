/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.K;

public class Porte extends Subsystem {
  
  private VictorSP moteurPorte;
  private DigitalInput switchHaut;
  private DigitalInput switchBas;


  @Override
  public void initDefaultCommand() {

  }

  public Porte() {

    moteurPorte = new VictorSP(K.Ports.PORTE_MOTEUR);
    addChild("Moteur porte", moteurPorte);
    
    switchHaut = new DigitalInput(K.Ports.PORTE_SWITCH_HAUT);
    addChild("Switch haut", switchHaut);

    switchBas = new DigitalInput(K.Ports.PORTE_SWITCH_BAS);
    addChild("Switch bas", switchBas);

  }

  public void ouvrirPorte() {
    moteurPorte.set(K.Porte.VITESSE_PORTE_OUVRIR);

  }
  public void fermerPorte() {
    moteurPorte.set(K.Porte.VITESSE_PORTE_FERMER);

  }
  public boolean estEnHaut() {
    return !switchHaut.get();
  }
  public boolean estEnBas(){
    return switchBas.get();
  }
  
  public void stopPorte() {
    moteurPorte.set(0.0);

  }

}
