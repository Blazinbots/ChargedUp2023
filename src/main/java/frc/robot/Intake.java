package frc.robot;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.Timer;

public class Intake {
  private Timer m_Timer;
  private static final int PWM_CHANNEL = 2;
  private Spark m_intake;
  private double m_speed = -0.1;
  private boolean m_lockIntakeMove = false;

  private boolean m_intake_isOpen = true;

  public enum POSITION {
    UP,
    DOWN
  }

  public void init() {
      m_Timer = new Timer();
      m_Timer.reset();
      m_intake = new Spark(PWM_CHANNEL);
  }

  public void opencloseIntake() {
    // Don't allow movement if already moving. Wait until complete
    if( m_lockIntakeMove ) {
      return;
    }

    m_speed = -m_speed;
    m_intake_isOpen = !m_intake_isOpen;
    m_Timer.reset();
    m_lockIntakeMove = true;
    
  }

  public void teleopPeriodic() {
    // Move motor for 0.2 seconds
    if(m_lockIntakeMove && !m_Timer.hasElapsed(0.2)) {
      m_intake.set(m_speed);
    } else {
      m_lockIntakeMove = false;
    }

  }

}

