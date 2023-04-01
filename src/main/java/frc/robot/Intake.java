package frc.robot;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.Timer;

public class Intake {
  private Timer m_Timer;
  private Spark m_intake;
  private double m_speed = -Configuration.Intake.speed;
  private boolean m_lockIntakeMove = false;

  public void init() {
      m_Timer = new Timer();
      m_Timer.reset();
      m_intake = new Spark(Configuration.Ports.Intake_PWM_Channel);
  }

  public void opencloseIntake() {
    // Don't allow movement if already moving. Wait until complete
    if( m_lockIntakeMove ) {
      return;
    }

    m_speed = -m_speed;
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

