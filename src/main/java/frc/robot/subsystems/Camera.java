/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.GripPipeline;
import frc.robot.K;

public class Camera extends Subsystem {

  enum Cible {
    CAROTTE,
    ATTERRISSAGE
  }

  private Cible cible = Cible.ATTERRISSAGE;

  private static UsbCamera camera;
  private GripPipeline gripPipeline;
  private Spark controlleur;

  Rectangle targetRectangle = null;

  private Thread thread;

  public Camera(){

    camera = new UsbCamera("Main Cam", 0);

    gripPipeline = new GripPipeline();

    controlleur = new Spark(K.Ports.PORT_LED_CAMERA);

    thread = new Thread(this::processusVision);
    thread.start();

    updateBrightness();
    updateExposure();
  }

  @Override
  public void periodic() {
    if(targetRectangle != null){
      SmartDashboard.putNumber("Largeur", getLargeur());
      SmartDashboard.putNumber("Centre X", getCenterX());
    }
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
        
        ArrayList<MatOfPoint> contours = gripPipeline.findContoursOutput();

        List<Rect> gripRects = new ArrayList<Rect>();
        for (MatOfPoint c : contours) {
          gripRects.add(Imgproc.boundingRect(c));
        }
        
        List<Rectangle> outRectangles = contours.stream()
        .map(Imgproc::boundingRect)
        .map(this::normalizeRect)
        .filter(this::filtrerRectangle)
        .sorted(this::ordonnerRectangles)
        .collect(Collectors.toList());


        if(outRectangles.size() > 0) {

          targetRectangle = outRectangles.get(0);

          for (int i = 0; i < gripRects.size(); i++) {
            Imgproc.rectangle(outputResize,
            new Point(gripRects.get(i).x, gripRects.get(i).y),
            new Point(gripRects.get(i).x + gripRects.get(i).width, gripRects.get(i).y+gripRects.get(i).height),
            //bgr
            new Scalar(0, 0, 255));
          }

          for (int i = 0; i < outRectangles.size(); i++) {
            Imgproc.rectangle(outputResize,
            new Point(outRectangles.get(i).rect.x, outRectangles.get(i).rect.y),
            new Point(outRectangles.get(i).rect.x + outRectangles.get(i).rect.width, outRectangles.get(i).rect.y+outRectangles.get(i).rect.height),
            //bgr
            new Scalar(30, 90, 250));
          }

          Imgproc.rectangle(outputResize,
            new Point(targetRectangle.rect.x, targetRectangle.rect.y),
            new Point(targetRectangle.rect.x + targetRectangle.rect.width, targetRectangle.rect.y+targetRectangle.rect.height),
            //bgr
            new Scalar(0, 255, 0));

        }
        
        videoResize.putFrame(outputResize);
        
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public synchronized double getCenterX(){
    return targetRectangle.centreX();
  }

  public synchronized double getLargeur(){
    return targetRectangle.width;
  }

  public void setCible(Cible _cible){
    cible = _cible;
  }

  public void startCamera(){
    camera.setBrightness(0);
    camera.setExposureManual(0);
    controlleur.set(1.0);
  }

  public void stopCamera(){
    camera.setBrightness(K.Camera.PILOT_BRIGHTNESS);
    camera.setExposureManual(K.Camera.PILOT_EXPOSURE);
    controlleur.set(0.0);
  }

  public static void updateBrightness(){
    camera.setBrightness(K.Camera.PILOT_BRIGHTNESS);
  }

  public static void updateExposure(){
    camera.setExposureManual(K.Camera.PILOT_EXPOSURE);
  }

  @Override
  public void initDefaultCommand() {
  }

  public Rectangle normalizeRect(Rect rect){
    double normalizedX = 2 * rect.x  / (double)K.Camera.WIDTH - 1;
    double normalizedY = 1 - 2 * rect.y / (double)K.Camera.HEIGHT;
    double normalizedW = 2 * rect.width  / (double)K.Camera.WIDTH;
    double normalizedH = 2 * rect.height / (double)K.Camera.HEIGHT;

    return new Rectangle(normalizedX, normalizedY, normalizedW, normalizedH, rect);
  }

  public boolean filtrerRectangle(Rectangle rectangle) {
    
    if(rectangle.ratio() < 1)
      return false;    

    if(cible == Cible.ATTERRISSAGE && rectangle.ratio() >= 6 & rectangle.ratio() < 6 + K.Camera.RATIO_OFFSET_ATTERRISSAGE)
      return false;

    if(cible == Cible.CAROTTE && rectangle.ratio() != 2)
      return false;

    return true;
  }

  public int ordonnerRectangles(Rectangle dernier, Rectangle aComparer){
    if(cible == Cible.ATTERRISSAGE){

      if(dernier.scoreAtterissage() < aComparer.scoreAtterissage()){
        return 1;
      } 

      else if(dernier.scoreAtterissage() > aComparer.scoreAtterissage()){
        return -1;
      }

    }else{

      if(dernier.scoreCarotte() < aComparer.scoreCarotte()){
        return 1;
      } 

      else if(dernier.scoreCarotte() > aComparer.scoreCarotte()){
        return -1;
      }

    }


    return 0;
  }
 
  class Rectangle{

      public double x, y, width, height;
      public Rect rect;

      public Rectangle(double x, double y, double width, double height, Rect rect){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rect = rect;
      }

      public double ratio(){
        return width / height;
      }

      public double scoreCarotte(){
        return -50 * Math.abs(ratio() - 2) + 1;
      }

      public double scoreAtterissage(){
        return -0.5 * Math.abs(ratio() - 6) + 1;
      }
      
      public double centreX(){
        return x + width/2;
      }
    }
}
