import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.lcd.LCD;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Driver {
    public static void main(String[] args) {
        System.out.println("Welcome to Our Morse Code Robot!");
        System.out.println("Authors: Samuel Haddock, Yash Kumar and Miski Hussein");
        System.out.println("Version: 1.0");
        System.out.println("Press Enter button to continue...");
        Button.ENTER.waitForPress();

        BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(MotorPort.A);
        BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(MotorPort.B);

        LCD.clear();
        @SuppressWarnings("deprecation")
		MovePilot pilot = new MovePilot(5.6f, 12.0f, Motor.A, Motor.B); // Fixed motor references

        // Initialise the sound sensor
        NXTSoundSensor soundSensor = new NXTSoundSensor(SensorPort.S2);
        SampleProvider soundMode = soundSensor.getDBAMode();

        Behavior forwardBehavior = new Trundle(pilot);
        Behavior avoidWallBehavior = new Backup(SensorPort.S3, pilot);
        Behavior lowBatteryBehavior = new LowBattery();
        Behavior darkChecker = new DarkChecker(pilot, SensorPort.S1);
        Behavior[] behaviors = {avoidWallBehavior, lowBatteryBehavior, darkChecker};
        Arbitrator arbitrator = new Arbitrator(behaviors);

        while (!Button.ENTER.isDown()) {
            arbitrator.go();
            squareCommand(mL, mR); // Fixed method call
        }

        // Stop the arbitrator and close the sound sensor when the enter button is pressed
        arbitrator.stop();
        soundSensor.close();
    }

    public static void squareCommand(BaseRegulatedMotor mL, BaseRegulatedMotor mR) { // Fixed method name
        final float WHEEL_DIAMETER = 56;
        final float AXLE_LENGTH = 44;
        final float ANGULAR_SPEED = 200;
        final float LINEAR_SPEED = 200;

        Wheel wLeft = WheeledChassis.modelWheel(mL, WHEEL_DIAMETER).offset(-AXLE_LENGTH / 2);
        Wheel wRight = WheeledChassis.modelWheel(mR, WHEEL_DIAMETER).offset(AXLE_LENGTH / 2);

        Wheel[] wheels = new Wheel[] {wRight, wLeft}; // Corrected the order of wheels

        WheeledChassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
        MovePilot pilot = new MovePilot(chassis);

        // Using OdometryPoseProvider
        PoseProvider poseProvider = new OdometryPoseProvider(pilot);

        for (int side = 0; side < 4; side++) {
            pilot.setAngularSpeed(ANGULAR_SPEED);
            pilot.setLinearSpeed(LINEAR_SPEED);
            pilot.travel(300);
            pilot.rotate(90); // Changed the angle to make a square
        }

        // Close motor ports to release resources
        mL.close();
        mR.close();
    }

    public void circleCommand() {

    }

    public void freeRoamCommand() {

    }

    public void danceCommand() {

    }
}



