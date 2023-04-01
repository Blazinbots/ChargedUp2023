package frc.robot;

public class Configuration {

    // XBox controller layout (carry over from 2021 code)
    public class Controller {
        static final int BUTTON_A = 1;
        static final int BUTTON_B = 2;
        static final int BUTTON_X = 3;
        static final int BUTTON_Y = 4;
        static final int BUTTON_LT = 5;
        static final int BUTTON_RT = 6;
        static final int BUTTON_BACK = 7;
        static final int BUTTON_START = 8;
        static final int BUTTON_L_ANALOG = 9;
        static final int BUTTON_R_ANALOG = 10;      
    }

    // PWM ports connected to on RoboRio
    public class Ports {
        static final int LeftMotor = 0;
        static final int RightMotor = 1;
        static final int Intake_PWM_Channel = 2;
    }

    public class Intake {
        static final double speed = 0.1;
    }

    public class Arm {
        static final int deviceID = 2; // CAN Device ID for SparkMax

        // Number of rotations of the motor. Note that the output movement will be geared down:
        // 16:1 planetary gearbox + 26:16 cog ratio. So this should roughly correspond to
        // rotations/26
        static final int upPosition = 4;
        static final int downPosition = 0;
    }
}