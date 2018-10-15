/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.GripPipeline;

public class Camera extends Subsystem {

  private UsbCamera camera;
  private GripPipeline gripPipeline;

  public Camera(){

    camera = new UsbCamera("Main Cam", 0);
    camera.setBrightness(0);
    camera.setExposureManual(0);


    gripPipeline = new GripPipeline();

    Thread thread = new Thread(this::processusVision);
    thread.start();
  }

  @Override
  public void initDefaultCommand() {

  }

  private void processusVision(){

    CvSink source = CameraServer.getInstance().getVideo(camera);
    Mat image = new Mat();
    
    while(!Thread.interrupted()){
      
      source.grabFrame(image);
      
      try {
        
        gripPipeline.process(image);

      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }

}
