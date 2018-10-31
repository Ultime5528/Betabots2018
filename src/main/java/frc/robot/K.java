/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * determiner les ports et variables.
 */
public class K {

  public static class Ports {

    public static final int ENCODEUR_DROITE_A = 4;
    public static final int ENCODEUR_DROITE_B = 5;

    public static final int ENCODEUR_GAUCHE_A = 4;
    public static final int ENCODEUR_GAUCHE_B = 5;

    // PWM
    public static final int BASE_PILOTABLE_MOTEUR_DROIT = 0;
    public static final int BASE_PILOTABLE_MOTEUR_GAUCHE = 1;
    public static final int INTAKE_MOTEUR_TRAPE = 2;
    public static final int INTAKE_MOTEUR = 3;


    // Analogs
    public static final int INTAKE_POTENTIOMETRE = 0;

  }

  public static class BasePilotable {

  }

  public static class Intake {
    public static double VITESSE_GOBER = 0.2; 
    public static double VITESSE_CRACHER = -0.2;
  }

}