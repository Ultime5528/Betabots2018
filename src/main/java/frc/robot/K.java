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
  
  public static class Camera {
    public static int WIDTH = 320;
    public static int HEIGHT = 240;

    public static int PILOT_BRIGHTNESS = 0;
    public static int PILOT_EXPOSURE = 0;

    public static double RATIO_OFFSET_ATTERRISSAGE = 1.0;
    
    //Viser
    public static double X_THRESHOLD = 0.05;

    public static double LARGEUR_TARGET = 0.155;
    public static double LARGEUR_THRESHOLD = 0.016;

    public static double TURN_SPEED = 0.55;
    public static double FORWARD_SPEED = 0.6;

    //Process
    //HSV
    public static double H_MIN = 68;
    public static double H_MAX = 112;

		public static double S_MIN = 62;
    public static double S_MAX = 255.0;

    public static double V_MIN = 82;
    public static double V_MAX = 255;

  }

}