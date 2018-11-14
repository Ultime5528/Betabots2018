/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.filters.LinearDigitalFilter;
import frc.robot.ADIS16448_IMU;
import frc.robot.K;
import frc.robot.Robot;
import frc.robot.ADIS16448_IMU.Axis;
import frc.robot.commands.Piloter;

public class BasePilotable extends Subsystem {

  private VictorSP moteurDroit;
  private VictorSP moteurGauche;
  private DifferentialDrive drive;
  private ADIS16448_IMU gyro;
  private Encoder encodeurGauche;
  private Encoder encodeurDroite;
  private LinearDigitalFilter averageSpeedFilter;
  private PIDSource averageSpeed;

  public BasePilotable() {

    moteurDroit = new VictorSP(K.Ports.BASE_PILOTABLE_MOTEUR_DROIT);
    addChild("Moteur Droit", moteurDroit);

    moteurGauche = new VictorSP(K.Ports.BASE_PILOTABLE_MOTEUR_GAUCHE);
    addChild("Moteur Gauche", moteurGauche);

    drive = new DifferentialDrive(moteurGauche, moteurDroit);
    addChild("Drive", drive);

    gyro = new ADIS16448_IMU(Axis.kY);
		gyro.calibrate();
    addChild("Gyro", gyro);

    encodeurDroite = new Encoder(K.Ports.ENCODEUR_DROITE_A, K.Ports.ENCODEUR_DROITE_B);
    addChild("Encodeur Droite", encodeurDroite);
    
    encodeurGauche = new Encoder(K.Ports.ENCODEUR_GAUCHE_A, K.Ports.ENCODEUR_GAUCHE_B);
    addChild("Encodeur Gauche", encodeurGauche);

    averageSpeed = new PIDSource() {

			@Override

			public void setPIDSourceType(PIDSourceType pidSource) {

			}

			@Override

			public double pidGet() {

				return (encodeurDroite.getRate() + encodeurGauche.getRate()) / 2;

			}

			

			@Override

			public PIDSourceType getPIDSourceType() {

				return PIDSourceType.kRate;

			}

    };
    
    
		averageSpeedFilter = LinearDigitalFilter.movingAverage(averageSpeed, 10);
    
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Piloter());
  }

  public void arcadeDrive(Joystick joystick) {

    drive.arcadeDrive(-joystick.getY(), -joystick.getX());

  }

  public void stop() {
    moteurDroit.set(0);
    moteurGauche.set(0);
  }

  public void resetGyro() {
    gyro.reset();
  }

  public void resetEncoders() {
    encodeurGauche.reset();
    encodeurDroite.reset();
  }

  public double getEncoderGaucheDistance() {

    return encodeurGauche.getDistance();

  }

  public double getEncoderDroiteDistance() {

    return encodeurDroite.getDistance();

  }

  
	public void tankDrive(double left, double right) {

		drive.tankDrive(left, right, false);

	}

	

	public void drive() {

		Joystick joystick = Robot.oi.getJoystick();

		drive.arcadeDrive(-Robot.oi.getInterY().interpolate(-joystick.getY()), -joystick.getX());

  }

  
  

	public double getAverageSpeed() {

		return averageSpeed.pidGet();

	}

	public LinearDigitalFilter getAverageSpeedFilter() {

		return averageSpeedFilter;

  }
  
	public double getHeading() {

    return -gyro.getAngle();

	}

}