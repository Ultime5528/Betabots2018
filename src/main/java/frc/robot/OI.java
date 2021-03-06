/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.ADIS16448_IMU.Axis;
import frc.robot.commands.autonomes.*;
import frc.robot.commands.BougerTreuil;
import frc.robot.commands.CracherBalles;
import frc.robot.commands.PrendreBalles;
import frc.robot.triggers.AxisDownTrigger;
import frc.robot.triggers.AxisUpTrigger;
import frc.robot.triggers.POVTrigger;
import frc.robot.triggers.POVTrigger.Arrow;
import frc.robot.commands.Viser;
import frc.robot.commands.ViserAvancer;
import frc.robot.util.CubicInterpolator;
import frc.robot.commands.DescendreTreuil;
import frc.robot.commands.FermerPorte;
import frc.robot.commands.MonterTreuil;
import frc.robot.commands.OuvrirPorte;

public class OI {

  private Joystick joystick;
  private XboxController xbox;
  private JoystickButton buttonA;
  private JoystickButton buttonB;
  private JoystickButton buttonX;
  private JoystickButton buttonY;
  private AxisDownTrigger buttonLT;
  private AxisDownTrigger buttonRT;
  private AxisUpTrigger joystickGaucheHaut;
  private AxisDownTrigger joystickGaucheBas;
  private POVTrigger croixHaut;
  private POVTrigger croixBas;
  private JoystickButton buttonStart, buttonBack;

  private CubicInterpolator interY;

  public OI() {

    interY = new CubicInterpolator(K.BasePilotable.INTERY_COURBURE, K.BasePilotable.INTERY_DEADZONE_VITESSE,
        K.BasePilotable.INTERY_DEADZONE_JOYSTICK);

    joystick = new Joystick(0);
    xbox = new XboxController(1);

    buttonA = new JoystickButton(xbox, 1);
    buttonA.toggleWhenPressed(new BougerTreuil(K.Intake.TREUIL_POT_MIN));

    buttonB = new JoystickButton(xbox, 2);
    buttonB.toggleWhenPressed(new BougerTreuil(K.Intake.TREUIL_HAUTEUR_CAROTTE));

    buttonStart = new JoystickButton(xbox, 8);
    buttonStart.toggleWhenPressed(new Viser());

    buttonBack = new JoystickButton(xbox, 7);
    buttonBack.toggleWhenPressed(new ViserAvancer());

    buttonX = new JoystickButton(xbox, 3);
    buttonX.toggleWhenPressed(new BougerTreuil(K.Intake.TREUIL_HAUTEUR_BALLE));

    buttonY = new JoystickButton(xbox, 4);
    buttonY.toggleWhenPressed(new BougerTreuil(K.Intake.TREUIL_POT_MAX));

    buttonLT = new AxisDownTrigger(xbox, 2);
    buttonLT.toggleWhenActive(new CracherBalles());

    buttonRT = new AxisDownTrigger(xbox, 3);
    buttonRT.toggleWhenActive(new PrendreBalles());

    joystickGaucheBas = new AxisDownTrigger(xbox, 1);
    joystickGaucheBas.whileActive(new DescendreTreuil());

    joystickGaucheHaut = new AxisUpTrigger(xbox, 1);
    joystickGaucheHaut.whileActive(new MonterTreuil());

    croixHaut = new POVTrigger(xbox, Arrow.UP);
    croixHaut.whenActive(new OuvrirPorte());

    croixBas = new POVTrigger(xbox, Arrow.DOWN);
    croixBas.whenActive(new FermerPorte());

    SmartDashboard.putData("AutonomePlateformeDroiteA", new AutoPlateformeDroiteA());

  }

  public Joystick getJoystick() {

    return joystick;

  }

  public CubicInterpolator getInterY() {

    return interY;

  }

}