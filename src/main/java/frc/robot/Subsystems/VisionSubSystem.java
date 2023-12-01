// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
public class VisionSubSystem extends SubsystemBase {

  //**ALL ANGLES IN RADIANS ALL DISTANCES IN METERS**//
  PhotonCamera mCamera;
  private static final VisionSubSystem INSTANCE = new VisionSubSystem();
  double mCameraHeight = 0;
  double mCameraPitch = 0;

  private boolean mDriverMode = false;
  public VisionSubSystem(){
    //Replace with name of cam
    mCamera = new PhotonCamera(getName());
  }

  @Override
  public void periodic() {
    
  }

  public static VisionSubSystem getInstance(){
    return INSTANCE;
  }

  public int getBestTagID(){
    return mCamera.getLatestResult().getBestTarget().getFiducialId();
  }

  public boolean hasTargets(){
    return mCamera.getLatestResult().hasTargets();
  }
  public Translation2d getTransDiff(double targetHeight){
    if(hasTargets()){

      //TODO might need to change theta to yaw instead of pitch not sure tho
      double theta = mCamera.getLatestResult().getBestTarget().getPitch();
      double distance = PhotonUtils.calculateDistanceToTargetMeters(
        mCameraHeight, 
        targetHeight, 
        mCameraPitch,
        theta);
      double xDiff = distance * Math.cos(theta);
      double yDiff = distance * Math.sin(theta);
      return new Translation2d(xDiff, yDiff);
    }
    else{
      return new Translation2d();
    }
  }

  public void toggleDriverMode(){
    mDriverMode = !mDriverMode;
    mCamera.setDriverMode(mDriverMode);
  }
  //Returns false if not in driver mode
  public boolean getDriverMode(){
    return mCamera.getDriverMode();
  }
}