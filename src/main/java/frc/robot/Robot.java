// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with split
 * arcade steering and an Xbox controller.
 */
public class Robot extends TimedRobot {

  private final Spark m_leftMotor = new Spark(Configuration.Ports.LeftMotor);
  private final Spark m_rightMotor = new Spark(Configuration.Ports.RightMotor);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);
  private final XboxController m_driverController = new XboxController(0);
  private final Arm m_arm = new Arm();
  private final Intake m_Intake = new Intake();
  private final Timer m_AutoTimer = new Timer();

  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightMotor.setInverted(true);
    m_arm.init();
    m_Intake.init();
  }

  @Override
  public void teleopPeriodic() {
    // Drive with split arcade drive.
    // That means that the Y axis of the left stick moves forward
    // and backward, and the X of the right stick turns left and right.
    m_robotDrive.arcadeDrive(-m_driverController.getLeftY(), -m_driverController.getRightX());
    
    // For intake movement
    m_Intake.teleopPeriodic();
    
    System.out.println("pos: " + m_arm.getPosition());

    if(m_driverController.getAButtonPressed()) {
      m_arm.setPosition(Arm.POSITION.UP);
    } 
    else if(m_driverController.getBButtonPressed()) {
      m_arm.setPosition(Arm.POSITION.DOWN);
    }
    
    if( m_driverController.getXButtonPressed() ) {
      m_Intake.opencloseIntake();
    }
    
  }

  @Override
  public void autonomousInit() {
    m_AutoTimer.reset();
    m_AutoTimer.start();
  }

  @Override
  public void autonomousPeriodic() {
    //m_arm.breakVelco();
    if(!m_AutoTimer.hasElapsed(3.0)) {
      m_robotDrive.arcadeDrive(0.6, 0.0);
    } else {
      m_robotDrive.arcadeDrive(0.0, 0.0);
    }
  }

}
