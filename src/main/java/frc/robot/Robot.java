/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Camera;
import frc.Properties;
import frc.robot.commands.autonomes.AutoPlateformeDroiteA;
import frc.robot.commands.autonomes.AutoPlateformeDroiteD;
import frc.robot.commands.autonomes.AutoPlateformeGaucheA;
import frc.robot.commands.autonomes.AutoPlateformeGaucheD;
import frc.robot.commands.autonomes.Autonome;
import frc.robot.commands.autonomes.AutonomeDescendre;
import frc.robot.subsystems.BasePilotable;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Porte;
import frc.robot.subsystems.Treuil;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static BasePilotable basePilotable;
  public static OI oi;
  public static Intake intake = new Intake();
  public static Treuil treuil = new Treuil();
  public static Porte porte = new Porte();

  public static Camera camera = new Camera();

  private Properties properties = new Properties(K.class);

  private Command autonomeChoisi;

  private SendableChooser<Autonome> chooser;

  private BadLog log;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    log = BadLog.init("/media/sda1/Badlog/test.bag");
    BadLog.createValue("MatchNumber", "" + DriverStation.getInstance().getMatchNumber());
                  
    BadLog.createTopic("", "", () -> .2);

    BadLog.createTopicSubscriber("RandomNumbers", BadLog.UNITLESS, badlog.lib.DataInferMode.DEFAULT, "integrate");

    

			BadLog.createValue("Start Time",       LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyy H:mm:ss", Locale.CANADA_FRENCH)));
			BadLog.createValue("Event Name",
					Optional.ofNullable(DriverStation.getInstance().getEventName()).orElse(""));
			BadLog.createValue("Match Type", DriverStation.getInstance().getMatchType().toString());
			BadLog.createValue("Match Number", "" + DriverStation.getInstance().getMatchNumber());
			BadLog.createValue("Alliance", DriverStation.getInstance().getAlliance().toString());
			BadLog.createValue("Location", "" + DriverStation.getInstance().getLocation());

			BadLog.createTopicSubscriber("Time", "s", badlog.lib.DataInferMode.DEFAULT, "hide", "delta", "xaxis");

			BadLog.createTopicStr("System/Browned Out", "bool", () -> RobotController.isBrownedOut() ? "1" : "0");
			BadLog.createTopic("System/Battery Voltage", "V", () -> RobotController.getBatteryVoltage());
			BadLog.createTopicStr("System/FPGA Active", "bool", () -> RobotController.isSysActive() ? "1" : "0");
			BadLog.createTopic("Match Time", "s", () -> DriverStation.getInstance().getMatchTime());


    basePilotable = new BasePilotable();

    oi = new OI();

    chooser = new SendableChooser<>();

    chooser.addDefault("Droite A,C", new AutoPlateformeDroiteA());
    chooser.addObject("Droite B,D", new AutoPlateformeDroiteD());
    chooser.addObject("Gauche A,C", new AutoPlateformeGaucheA());
    chooser.addObject("Gauche B,D", new AutoPlateformeGaucheD());

    SmartDashboard.putData("Autonomne", chooser);
    // Subsystemscanaddtheirowntopicsandvalues
    log.finishInitialization();
  }
  
  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   **/
  @Override
  public void robotPeriodic() {
    properties.performChanges();
    SmartDashboard.putNumber("Pot", Robot.treuil.getPot());
    BadLog.publish("RandomNumbers", Math.random());
    log.updateTopics();
    log.log();
  }
  
  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
   */
  @Override
  public void disabledInit() {
  }
  
  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }
  
  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString code to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons to
   * the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    
    autonomeChoisi = new AutonomeDescendre();//chooser.getSelected(); 
    
    if (autonomeChoisi != null)
    autonomeChoisi.start();
  }
  
  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }
  
  @Override
  public void teleopInit() {
    if (autonomeChoisi != null)
    autonomeChoisi.cancel();
  }
  
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }
  
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
