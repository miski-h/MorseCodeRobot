import lejos.hardware.Bluetooth;
import lejos.robotics.subsumption.Behavior;

public class Bluetooth implements Behavior {
    private boolean suppressed = false;
    private boolean bluetoothMessageAvailable = false;

    // Returns true if a Bluetooth message is available
    public boolean takeControl() {
        return Bluetooth.getNXTCommConnector().available() > 0;
    }

    // Just notifies the action method by setting a field to true
    public void suppress() {
        suppressed = true;
    }

    // Sets a state variable for further action after Bluetooth behavior completes
    public void action() {
        suppressed = false;
        bluetoothMessageAvailable = true;
    }

    // Method to check if a Bluetooth message is available
    public boolean isBluetoothMessageAvailable() {
        return bluetoothMessageAvailable;
    }

    // Method to reset the state of Bluetooth message availability
    public void resetBluetoothMessageAvailability() {
        bluetoothMessageAvailable = false;
    }
}
