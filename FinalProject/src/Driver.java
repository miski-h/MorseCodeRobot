import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.MovePilot;
import lejos.hardware.port.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.hardware.Button;

public class Driver {
    public static void main(String[] args) {

        System.out.println("Welcome to Our Robot Program!");
    	System.out.println("Authors: Samuel Haddock, Yash Kumar and Miski Hussein");
    	System.out.println("Version: 1.0");

    	System.out.println("Press any button to continue...");
    	Button.waitForAnyPress();
        
        @SuppressWarnings("deprecation")
		MovePilot pilot = new MovePilot(5.6f, 12.0f, Motor.A, Motor.B);

        Behavior forwardBehavior = new Trundle(pilot);
        Behavior avoidWallBehavior = new Backup(SensorPort.S3, pilot);
        Behavior lowBatteryBehavior = new Battery();

        Behavior[] behaviors = {forwardBehavior, avoidWallBehavior, lowBatteryBehavior};

        Arbitrator arbitrator = new Arbitrator(behaviors);

        arbitrator.go();
    }
}





