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

    public static final int BASE_PILOTABLE_MOTEUR_DROIT = 0;
    public static final int BASE_PILOTABLE_MOTEUR_GAUCHE = 1;
  

  }

  public static class BasePilotable {

    
  }

  public static class Camera {
    public static int WIDTH = 320;
    public static int HEIGHT = 240;
    public static double MIN_Y = -0.8;
    public static double MAX_Y = 0.8;

    //Process
    //HSV
    public static double H_MIN = 11.33;
    public static double H_MAX = 89.38;

		public static double S_MIN = 133.0;
    public static double S_MAX = 255.0;

    public static double V_MIN = 255.0;
    public static double V_MAX = 126.12;

  }



}
