/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.GripPipeline;
import frc.robot.K;

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
    CvSource videoHsv = CameraServer.getInstance().putVideo("Vision HSV", K.Camera.WIDTH, K.Camera.HEIGHT);
    CvSource videoResize = CameraServer.getInstance().putVideo("Vision Resize", K.Camera.WIDTH, K.Camera.HEIGHT);
    Mat input = new Mat(), outputResize, outputHsv;
    
    while(!Thread.interrupted()){
      
      try {
        source.grabFrame(input);
        gripPipeline.process(input, K.Camera.WIDTH, K.Camera.HEIGHT);

    
        outputHsv = gripPipeline.hsvThresholdOutput();
        videoHsv.putFrame(outputHsv);

        outputResize = gripPipeline.resizeImageOutput();
        videoResize.putFrame(outputResize);

        ArrayList<MatOfPoint> contours = gripPipeline.filterContoursOutput();

        for (MatOfPoint contour : contours) {
          
          Rect rect = Imgproc.boundingRect(contour);

          // calcul du centre en X
          double centreX = rect.width / 2.0 + rect.x;
          // mettre le centre X entre -1 et 1
          centreX = 2 * centreX  / K.Camera.WIDTH - 1;

          double yHaut = 1 - 2 * rect.y / (double)K.Camera.HEIGHT;
          double yBas = 1 - 2 * rect.br().y / (double)K.Camera.HEIGHT;      
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
