import lejos.robotics.SampleProvider;
import lejos.hardware.port.SensorPort;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class TestMorseCode {

	public static void main(String[] args) {
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(MotorPort.D);
		mL.forward(); // Start the ball rolling
		mR.forward();
		NXTSoundSensor us = new NXTSoundSensor(SensorPort.S1);
		SampleProvider sp = us.getDBAMode();
		float[] samples = new float[1];
		LCD.drawString("Its all quiet", 2, 1);
		sp.fetchSample(samples, 0); // Read Ahead Paradigm
		long startTime = 0;
		while (samples[0] < 0.5) {
		    // just keep swimming....
		    sp.fetchSample(samples, 0); // Read Ahead for next loop.
		}
		LCD.drawString("Wow - what a noise", 2, 1);
		startTime = System.currentTimeMillis(); // Record the start time of the sound
		while (samples[0] >= 0.5) {
		    sp.fetchSample(samples, 0); // Read ahead for next loop.
		}
		long duration = System.currentTimeMillis() - startTime; // Calculate duration of sound
		LCD.drawString("Sound Duration: " + duration + " ms", 2, 2); // Display sound duration
		mL.stop();
		mR.stop();

	}

}
