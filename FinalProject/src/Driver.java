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

        final BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(MotorPort.A);
        final BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(MotorPort.B);

        LCD.clear();

        // Initialise the sound sensor
        NXTSoundSensor soundSensor = new NXTSoundSensor(SensorPort.S2);
        SampleProvider soundMode = soundSensor.getDBAMode();

        circleCommand(mL, mR);

        /*Behavior forwardBehavior = new Trundle(pilot);
        Behavior avoidWallBehavior = new Backup(SensorPort.S3, pilot);
        Behavior lowBatteryBehavior = new LowBattery();
        Behavior darkChecker = new DarkChecker(pilot, SensorPort.S1);
        Behavior[] behaviors = {avoidWallBehavior, lowBatteryBehavior, darkChecker};
        Arbitrator arbitrator = new Arbitrator(behaviors);

        while (!Button.ENTER.isDown()) {
            arbitrator.go();
        }

        // Stop the arbitrator and close the sound sensor when the enter button is pressed
        arbitrator.stop();
        soundSensor.close();*/
    }

    public static void squareCommand(BaseRegulatedMotor mL, BaseRegulatedMotor mR) { // Fixed method name
    	System.out.print("Running square command...");
        final float WHEEL_DIAMETER = 56;
        final float AXLE_LENGTH = 44;
        final float ANGULAR_SPEED = 200;
        final float LINEAR_SPEED = 200;

        Wheel wLeft = WheeledChassis.modelWheel(mL, WHEEL_DIAMETER).offset(-AXLE_LENGTH / 2);
        Wheel wRight = WheeledChassis.modelWheel(mR, WHEEL_DIAMETER).offset(AXLE_LENGTH / 2);

        Wheel[] wheels = new Wheel[] {wRight, wLeft}; // Corrected the order of wheels

        WheeledChassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
        MovePilot pilot = new MovePilot(chassis);

        for (int side = 0; side < 4; side++) {
            pilot.setAngularSpeed(ANGULAR_SPEED);
            pilot.setLinearSpeed(LINEAR_SPEED);
            pilot.travel(500);
            pilot.rotate(275); // Changed the angle to make a square
        }

        // Close motor ports to release resources
        mL.close();
        mR.close();
    }

    public static void circleCommand(BaseRegulatedMotor mL, BaseRegulatedMotor mR) {
    	System.out.print("Running circle command...");
        mR.synchronizeWith(new BaseRegulatedMotor[] {mL});
        mR.startSynchronization(); 
        mR.rotate(720,true); 
        mR.rotate(720,true); 

        mL.endSynchronization(); 
        mL.waitComplete();
        mR.waitComplete();

    }

    public void freeRoamCommand() {
    	System.out.print("Running free roam command...");

    }

    public void danceCommand() {
    	System.out.print("Running dance command...");

    }
}



