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

    public static final int ENCODEUR_GAUCHE_A = 0;
    public static final int ENCODEUR_GAUCHE_B = 1;
    
    public static final int ENCODEUR_DROITE_A = 2;
    public static final int ENCODEUR_DROITE_B = 3;

    public static final int PORTE_SWITCH_BAS = 4;
    public static final int PORTE_SWITCH_HAUT = 5;


    // PWM
    public static final int BASE_PILOTABLE_MOTEUR_DROIT = 0;
    public static final int BASE_PILOTABLE_MOTEUR_GAUCHE = 1;
    public static final int INTAKE_MOTEUR_TREUIL = 8;
    public static final int INTAKE_MOTEUR_CONVOYEUR = 7;
    public static final int PORTE_MOTEUR = 9;


    // Analogs
    public static final int INTAKE_POTENTIOMETRE = 0;

  }

  public static class BasePilotable {

    public static final double VITESSE_BASE_PILOTABLE_MAX = 0.5 ;

  }

  public static class Intake {
    public static double VITESSE_GOBER = -0.8; 
    public static double VITESSE_CRACHER = 0.8;
    public static double VITESSE_TREUIL_DESCENDRE = -0.3;
    public static double VITESSE_TREUIL_MONTER = 0.4;
    public static double VITESSE_TREUIL_MAINTIEN = 0.1;

    public static double TREUIL_POT_MIN = 0.1;
    public static double TREUIL_POT_MAX = 0.30;
    public static double TREUIL_TRESHOLD = 0.05;
    public static double TREUIL_HAUTEUR_CAROTTE = 0.15;
    public static double TREUIL_HAUTEUR_BALLE = 0.11;
  }

  public static class Porte {
    
    public static double VITESSE_PORTE_OUVRIR = -1;
    public static double VITESSE_PORTE_FERMER = 1;
  }

}