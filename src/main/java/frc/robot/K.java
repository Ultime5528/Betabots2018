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

    public static final int ENCODEUR_DROITE_A = 2;
    public static final int ENCODEUR_DROITE_B = 3;

    public static final int SWITCH_HAUT = 6;

    public static final int ENCODEUR_GAUCHE_A = 0;
    public static final int ENCODEUR_GAUCHE_B = 1;

    // PWM
    public static final int BASE_PILOTABLE_MOTEUR_DROIT = 0;
    public static final int BASE_PILOTABLE_MOTEUR_GAUCHE = 1;
    public static final int INTAKE_MOTEUR_TREUIL = 8;
    public static final int INTAKE_MOTEUR_CONVOYEUR = 7;
    public static final int PORTE_MOTEUR = 4;


    // Analogs
    public static final int INTAKE_POTENTIOMETRE = 0;

  }

  public static class BasePilotable {

  }

  public static class Intake {
    public static double VITESSE_GOBER = -0.8; 
    public static double VITESSE_CRACHER = 0.8;
    public static double VITESSE_TREUIL_DESCENDRE = -0.2;
    public static double VITESSE_TREUIL_MONTER = 0.7;

    public static double TREUIL_POT_MIN = 0.0;
    public static double TREUIL_POT_MAX = 2.0;
  }

}