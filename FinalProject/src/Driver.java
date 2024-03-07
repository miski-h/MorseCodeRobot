import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.MovePilot;
import lejos.hardware.port.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.hardware.Button;
import lejos.hardware.sensor.EV3SoundSensor;
import lejos.hardware.sensor.SensorMode;

public class Driver {
    public static void main(String[] args) {

        System.out.println("Welcome to Our Morse Code Robot!");
        System.out.println("Authors: Samuel Haddock, Yash Kumar and Miski Hussein");
        System.out.println("Version: 1.0");

        System.out.println("Press Enter button to continue...");
        Button.ENTER.waitForPress();

        @SuppressWarnings("deprecation")
        MovePilot pilot = new MovePilot(5.6f, 12.0f, Motor.A, Motor.B);

        // Initialize the sound sensor
        EV3SoundSensor soundSensor = new EV3SoundSensor(SensorPort.S2);
        SensorMode soundMode = soundSensor.getDBA();

        // Create behavior to detect sound input
        Behavior soundDetectionBehavior = new SoundDetection(soundMode);

        Behavior forwardBehavior = new Trundle(pilot);
        Behavior avoidWallBehavior = new Backup(SensorPort.S3, pilot);
        Behavior lowBatteryBehavior = new LowBattery();
        Behavior darkChecker = new DarkChecker(pilot, SensorPort.S1);

        Behavior[] behaviors = {forwardBehavior, avoidWallBehavior, lowBatteryBehavior, darkChecker, soundDetectionBehavior};

        Arbitrator arbitrator = new Arbitrator(behaviors);

        while (true) {
            if (Button.ENTER.isDown()) {
                arbitrator.stop();
                break;
            }
            arbitrator.go();
        }

        // Close the sound sensor
        soundSensor.close();
    }
}

class SoundDetection implements Behavior {
    private SensorMode soundMode;
    private boolean suppressed = false;

    public SoundDetection(SensorMode soundMode) {
        this.soundMode = soundMode;
    }

    public boolean takeControl() {
        float[] sample = new float[soundMode.sampleSize()];
        soundMode.fetchSample(sample, 0);
        // If sound level is above a certain threshold, take control
        return sample[0] > 0.5; // You may need to adjust the threshold
    }

    public void action() {
        suppressed = false;
        System.out.println("Sound Detected!");
        long startTime = System.currentTimeMillis();
        while (!suppressed && takeControl()) {
            // Keep track of the duration of sound
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Sound Duration: " + duration + " milliseconds");
    }

    public void suppress() {
        suppressed = true;
    }
}
