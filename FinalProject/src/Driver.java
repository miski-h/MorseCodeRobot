import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.MovePilot;
import lejos.hardware.port.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Driver {
    public static void main(String[] args) {

        System.out.println("Welcome to Our Robot Program!");
    	System.out.println("Authors: Samuel Haddock, Yash Kumar and Miski Hussein");
    	System.out.println("Version: 1.0");

    	System.out.println("Press any button to continue...");
    	Button.waitForAnyPress();
        
        // Initialize MovePilot for driving
        MovePilot pilot = new MovePilot(5.6f, 12.0f, Motor.A, Motor.B);

        // Initialize behaviors
        Behavior forwardBehavior = new ForwardBehavior(pilot);
        Behavior avoidWallBehavior = new AvoidWallBehavior(SensorPort.S1, pilot);
        Behavior lowBatteryBehavior = new LowBatteryBehavior();

        // Construct an array of behaviors
        Behavior[] behaviors = {forwardBehavior, avoidWallBehavior, lowBatteryBehavior};

        // Initialize an arbitrator with the behaviors
        Arbitrator arbitrator = new Arbitrator(behaviors);

        // Start the arbitrator
        arbitrator.go();
    }
}





