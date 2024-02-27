import lejos.hardware.Battery;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.SampleProvider;


public class Driver implements Behavior {
    private static final int WALL_DISTANCE_THRESHOLD = 40;
    private EV3UltrasonicSensor ultrasonicSensor;
    private SampleProvider distanceProvider;
    private float[] sample;
    private BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
    private BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
    private boolean suppressed = false;
    private boolean hasTurned = false;
    private float LOW_LEVEL;


    public Driver() {
        ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S3);
        distanceProvider = ultrasonicSensor.getDistanceMode();
        sample = new float[distanceProvider.sampleSize()];
        LOW_LEVEL = Battery.getVoltage();
    }


    public boolean takeControl() {
        distanceProvider.fetchSample(sample, 0);
        float distance = sample[0] * 100;
        float voltLevel = Battery.getVoltage();
        System.out.println("Distance: " + distance);
        System.out.println("Voltage: " + voltLevel);
        
        if (voltLevel < LOW_LEVEL){
        	playSound();
        }
        
        return distance < WALL_DISTANCE_THRESHOLD;
    }


    public void suppress() {
        suppressed = true;
    }


    public void action() {
        suppressed = false;
        if (!suppressed) {
               backupAndTurn();
               hasTurned = true;
        }
    }

    private void playSound() {
        Sound.playTone(500, 500);
    }


    private void backupAndTurn() {
        System.out.println("Backing up and turning...");
        // Backup
        mLeft.setSpeed(300);
        mRight.setSpeed(300);
        mLeft.backward();
        mRight.backward();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Turn
        if (Math.random() < 0.5) {
            System.out.println("Turning left.");
            mLeft.rotate(-380);
        } else {
            System.out.println("Turning right.");
            mRight.rotate(-380);
        }
    }


    public static void main(String[] args) {
        Driver behavior = new Driver();
        while (true) {
            if (Button.ENTER.isDown()) {
                break;
            }
            if (behavior.takeControl()) {
                behavior.action();
            } else {
                behavior.mLeft.forward();
                behavior.mRight.forward();
                behavior.hasTurned = false; // Reset the flag when not in turning state
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        behavior.mLeft.close();
        behavior.mRight.close();
    }
}





