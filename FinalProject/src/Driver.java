import lejos.hardware.motor.Motor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.hardware.port.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.NXTSoundSensor;


public class Driver {
    public static void main(String[] args) {
        System.out.println("Welcome to Our Morse Code Robot!");
        System.out.println("Authors: Samuel Haddock, Yash Kumar and Miski Hussein");
        System.out.println("Version: 1.0");
        System.out.println("Press Enter button to continue...");
        Button.ENTER.waitForPress();
        
        LCD.clear();

        @SuppressWarnings("deprecation")
        MovePilot pilot = new MovePilot(5.6f, 12.0f, Motor.A, Motor.B);


        // Initialise the sound sensor
        NXTSoundSensor soundSensor = new NXTSoundSensor(SensorPort.S2);
        SampleProvider soundMode = soundSensor.getDBAMode();


        // Create behaviour to detect sound input
        SoundDetection soundDetectionBehavior = new SoundDetection(soundMode);
        Behavior forwardBehavior = new Trundle(pilot);
        Behavior avoidWallBehavior = new Backup(SensorPort.S3, pilot);
        Behavior lowBatteryBehavior = new LowBattery();
        Behavior darkChecker = new DarkChecker(pilot, SensorPort.S1);

        // Set the program to be running
        while (!Button.ENTER.isDown()) {
        	soundDetectionBehavior.setRunning(true);
        }
        
        Behavior[] behaviors = {avoidWallBehavior, lowBatteryBehavior, darkChecker, soundDetectionBehavior};
        Arbitrator arbitrator = new Arbitrator(behaviors);


        while (!Button.ENTER.isDown()) {
            arbitrator.go();
        }


        // Stop the arbitrator and close the sound sensor when the enter button is pressed
        arbitrator.stop();
        soundSensor.close();
    }
}





