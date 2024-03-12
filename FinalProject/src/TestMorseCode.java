import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.SensorMode;


public class TestMorseCode {
    public static void main(String[] args) {
        BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
        BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);


        NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S2);
        SensorMode sound = (SensorMode) ss.getDBAMode();
        SoundDetection clap = new SoundDetection(sound, 0.4f, 200, 600);


        float[] level = new float[1];


        int i = 0;

        System.out.print("Morse Code Heard:");
        while (i == 0) {
            if (Button.ENTER.isDown()) {
                i++;
                break;
            }
            
            clap.fetchSample(level, 0);
            if (level[0] == 1.0) {
                System.out.println("Dot heard");
            } else if (level[0] == 2.0) {
                System.out.println("Dash heard");
            }
        }


        mLeft.close();
        mRight.close();
    }
}





