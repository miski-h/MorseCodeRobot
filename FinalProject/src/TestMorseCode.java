import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.SensorMode;

public class TestMorseCode {
    public static void main(String[] args) throws InterruptedException {
        BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
        BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);

        NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S2);
        SensorMode sound = (SensorMode) ss.getDBAMode();
        SoundDetection clap = new SoundDetection(sound, 0.6f, 200, 500);

        float[] level = new float[1];

        boolean dashInProgress = false; // Track whether a dash is in progress
        System.out.print("Morse Code Heard:");
        while (true) {
            if (Button.ENTER.isDown()) {
                break;
            }

            clap.fetchSample(level, 0);
            if (level[0] == 1.0) {
                // Single clap (potential dot)
                Thread.sleep(300); // Wait for potential second clap within dash time gap
                clap.fetchSample(level, 0); // Fetch sample again
                if (level[0] == 2.0) {
                    System.out.println("Dash heard");
                    dashInProgress = true; // Set dash in progress when a dash is detected
                } else {
                    System.out.println("Dot heard");
                    dashInProgress = false; // Reset dash in progress
                }
            } else if (level[0] == 2.0 && !dashInProgress) {
                // Only detect dash if not already in progress
                System.out.println("Dash heard");
                dashInProgress = true; // Set dash in progress when a dash is detected
            } else {
                dashInProgress = false; // Reset dash in progress if no clap is detected
            }
        }

        mLeft.close();
        mRight.close();
    }
}



