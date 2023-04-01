package frc.robot;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.Timer;

public class Intake {
  private Timer m_Timer;
  private Spark m_intake;
  private double m_speed = -Configuration.Intake.speed;
  private boolean m_lockIntakeMove = false;
  private boolean m_isOpen = true;

  public void init() {
      m_Timer = new Timer();
      m_Timer.reset();
      m_Timer.start();
      m_intake = new Spark(Configuration.Ports.Intake_PWM_Channel);
      m_intake.set(0.0);
  }

  public void opencloseIntake() {
    // Don't allow movement if already moving. Wait until complete
    if( m_lockIntakeMove ) {
      return;
    }

    m_speed = -m_speed;
    m_isOpen = !m_isOpen;
    m_Timer.reset();
    m_Timer.start();
    m_lockIntakeMove = true;
    
  }

  public void teleopPeriodic() {
    // Move motor for 0.2 seconds
    if(m_lockIntakeMove && !m_Timer.hasElapsed(Configuration.Intake.moveTime)) {
      m_intake.set(m_speed);
    } else {
      m_lockIntakeMove = false;
      m_Timer.stop();
      if(!m_isOpen) {
        m_intake.set(0.3);
      } else {
        m_intake.set(0.0);
      }
    }

  }

  public void setSpeed(double speed) {
    m_intake.set(speed);

  }

}

