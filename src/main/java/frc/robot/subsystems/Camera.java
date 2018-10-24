/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.Optional;

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

  private final double TARGET_RATIO_CARROTTE = 2.0;
  private final double TARGET_RATIO_ATTERISSAGE = 6.0;

  public Camera(){

    camera = new UsbCamera("Main Cam", 0);
    camera.setBrightness(0);
    camera.setExposureManual(0);


    gripPipeline = new GripPipeline();

    Thread thread = new Thread(this::processusVision);
    thread.start();
  }

  private void processusVision(){
    CvSink source = CameraServer.getInstance().getVideo(camera);
    CvSource videoHsv = CameraServer.getInstance().putVideo("Vision HSV", K.Camera.WIDTH, K.Camera.HEIGHT);
    CvSource videoResize = CameraServer.getInstance().putVideo("Vision Resize", K.Camera.WIDTH, K.Camera.HEIGHT);
    Mat input = new Mat(), outputResize, outputHsv;
    
    while(!Thread.interrupted()){
      
      try {
        source.grabFrame(input);
        gripPipeline.process(input);

    
        outputHsv = gripPipeline.hsvThresholdOutput();
        videoHsv.putFrame(outputHsv);

        outputResize = gripPipeline.resizeImageOutput();
        videoResize.putFrame(outputResize);

        ArrayList<MatOfPoint> contours = gripPipeline.filterContoursOutput();

        Optional<Rectangle> targetRectangleOpt = contours.stream()
          .map(Imgproc::boundingRect)
          .map(this::normalizeRect)
          .filter(this::filtrerRectangle)
          .sorted(this::ordonnerRectangles)
          .findFirst();

        Rectangle targetRectangle = null;
        if(targetRectangleOpt.isPresent()){
          targetRectangle = targetRectangleOpt.get();
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void initDefaultCommand() {
  }

  public Rectangle normalizeRect(Rect rect){
    double normalizedX = 2 * rect.x  / (double)K.Camera.WIDTH - 1;
    double normalizedY = 1 - 2 * rect.y / (double)K.Camera.HEIGHT;
    double normalizedW = 2 * rect.width  / (double)K.Camera.WIDTH;
    double normalizedH = 2 * rect.height / (double)K.Camera.HEIGHT;

    return new Rectangle(normalizedX, normalizedY, normalizedW, normalizedH);
  }

  public boolean filtrerRectangle(Rectangle rectangle) {
    
    if(rectangle.ratio() < 1)
      return false;    

    return true;
  }

  public int ordonnerRectangles(Rectangle dernier, Rectangle aComparer){

    if(dernier.scoreCarotte() < aComparer.scoreCarotte()){
      return 1;
    } 

    else if(dernier.scoreCarotte() > aComparer.scoreCarotte()){
      return -1;
    }
    
    return 0;
  }
 
  private class Rectangle{

      public double x, y, width, height;

      public Rectangle(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
      }

      public double ratio(){
        return width / height;
      }

      public double scoreCarotte(){
        return -50 * Math.abs(ratio()-2) + 1;
      }

      public double scoreAtterissage(){
        return -0.5 * Math.abs(ratio()-6) + 1;
      }

      
    }
}
