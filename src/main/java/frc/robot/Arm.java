package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Arm {
  private static final int deviceID = 2;
  private CANSparkMax m_motor;
  private SparkMaxPIDController m_pidController;
  private RelativeEncoder m_encoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
  // Number of rotations of the motor. Note that the output movement will be geared down:
  // 16:1 planetary gearbox + 26:16 cog ratio. So this should roughly correspond to
  // rotations/26
  private int upPosition = 4; 
  private int downPosition = 0;
  private double currentPosition = 0;


  public enum POSITION {
    UP,
    DOWN
  }

  public void init() {
      // initialize motor
      m_motor = new CANSparkMax(deviceID, MotorType.kBrushless);

      /**
       * The restoreFactoryDefaults method can be used to reset the configuration parameters
       * in the SPARK MAX to their factory default state. If no argument is passed, these
       * parameters will not persist between power cycles
       */
      m_motor.restoreFactoryDefaults();
  
      /**
       * In order to use PID functionality for a controller, a SparkMaxPIDController object
       * is constructed by calling the getPIDController() method on an existing
       * CANSparkMax object
       */
      m_pidController = m_motor.getPIDController();
  
      // Encoder object created to display position values
      m_encoder = m_motor.getEncoder();
  
      // PID coefficients
      kP = 0.3; // Default 0.1 (Proportional Gain used to get to setpoint quickly, may overshoot)
      kI = 1e-5; // Default 1e-4 (Integral Gain used to get to setpoint eventually)
      kD = 0.5;  // Default 1 (Derivative Gain used to dampen to avoid overshoot)
      kIz = 0; // Default 0
      kFF = 0;  // Default 0 (Feed Forward used as constant voltage to hold motors with high load)
      kMaxOutput = 0.2; // Default 1 (full speed forward)
      kMinOutput = -0.2; // Default -1 (full speed reverse)
  
      // set PID coefficients
      m_pidController.setP(kP);
      m_pidController.setI(kI);
      m_pidController.setD(kD);
      m_pidController.setIZone(kIz);
      m_pidController.setFF(kFF);
      m_pidController.setOutputRange(kMinOutput, kMaxOutput);  
  }

  public void setPosition(POSITION POS) {
    //calcuate rotations
    int numRotations = 0;
    currentPosition = m_encoder.getPosition();
    if(POS == POSITION.UP) {
      numRotations = upPosition - (int)currentPosition;
    } else { // Assume Down position
      numRotations = downPosition - (int)currentPosition;
    }

    m_pidController.setReference(numRotations, CANSparkMax.ControlType.kPosition);
  }

  public double getPosition() {
    return m_encoder.getPosition();
  }
}

