// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
public class VisionSubSystem extends SubsystemBase {

//ALL ANGLES IN RADIANS
  PhotonCamera mCamera;
  VisionSubSystem INSTANCE;
  double mCameraHeight;
  double mCameraPitch;
  public VisionSubSystem(double cameraHeight, double cameraPitch){
    //Replace with name of cam
    mCameraHeight = cameraHeight;
    mCameraPitch = cameraPitch;
    mCamera = new PhotonCamera(getName());
    INSTANCE = new VisionSubSystem(mCameraHeight, mCameraPitch);
  }

  @Override
  public void periodic() {
    
  }

  public VisionSubSystem getInstance(){
    return INSTANCE;
  }

  public int getBestTagID(){
    return mCamera.getLatestResult().getBestTarget().getFiducialId();
  }

  public boolean hasTargets(){
    return mCamera.getLatestResult().hasTargets();
  }

  public double getRange(double targetHeight){
    if(hasTargets()){
      return PhotonUtils.calculateDistanceToTargetMeters(mCameraHeight, targetHeight, mCameraPitch, Units.degreesToRadians(mCamera.getLatestResult().getBestTarget().getPitch()));
    }
    else{
      return 0;
    }
  }
}
