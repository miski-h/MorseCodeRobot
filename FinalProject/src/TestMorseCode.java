import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.SensorMode;
import java.util.ArrayList;

public class TestMorseCode {
    public static void main(String[] args) throws InterruptedException {

        NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S2);
        SensorMode sound = (SensorMode) ss.getDBAMode();
        SoundDetection clap = new SoundDetection(sound, 0.6f, 200, 500);

        float[] level = new float[1];
        ArrayList<String> morseList = new ArrayList<>(); // List to store dots and dashes

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
                    morseList.add("-"); // Add dash to list
                    dashInProgress = true; // Set dash in progress when a dash is detected
                } else {
                    morseList.add("."); // Add dot to list
                    dashInProgress = false; // Reset dash in progress
                }
            } else if (level[0] == 2.0 && !dashInProgress) {
                // Only detect dash if not already in progress
                morseList.add("-"); // Add dash to list
                dashInProgress = true; // Set dash in progress when a dash is detected
            } else {
                dashInProgress = false; // Reset dash in progress if no clap is detected
            }
        }

        // Print the Morse code sequence
        for (String element : morseList) {
            System.out.print(element + " ");
        }
        System.out.println();
        
        Button.ENTER.waitForPress();
    }
}



