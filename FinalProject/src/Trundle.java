import lejos.robotics.subsumption.Behavior;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.MovePilot;

public class Trundle implements Behavior {
    private boolean suppressed = false;
    private MovePilot pilot;

    public ForwardBehavior(MovePilot pilot) {
        this.pilot = pilot;
    }

    // Always returns true to indicate this behaviour should take control.
    public boolean takeControl() {
        return true;
    }

    // Makes the MovePilot go forward and then stops it.
    public void action() {
        suppressed = false;
        // Move forward
        pilot.forward();
        while (!suppressed) {
            Thread.yield();
        }
        // Stop the pilot
        pilot.stop();
    }

    // Notifies the action method to exit.
    public void suppress() {
        suppressed = true;
    }
}
