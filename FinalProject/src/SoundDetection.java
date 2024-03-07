import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;


class SoundDetection implements Behavior {
    private SampleProvider soundMode;
    private boolean suppressed = false;
    private boolean isRunning = false;
    private final float SOUND_THRESHOLD = 1.0f; // Adjust threshold as needed


    public SoundDetection(SampleProvider soundMode) {
        this.soundMode = soundMode;
    }


    public boolean takeControl() {
        float[] sample = new float[soundMode.sampleSize()];
        soundMode.fetchSample(sample, 0);
        // If sound level is above a certain threshold and the program is running, take control
        System.out.print("Checking Condition!");
        return isRunning && sample[0] > SOUND_THRESHOLD;
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


    // Method to set the running state of the program
    public void setRunning(boolean running) {
        isRunning = running;
    }


    // Method to check if the program is running
    public boolean isRunning() {
        return isRunning;
    }
}





