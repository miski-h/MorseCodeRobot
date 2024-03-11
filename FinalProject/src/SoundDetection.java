import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class SoundDetection implements SampleProvider {
    private final float threshold;
    private final int timeGap;
    private final SampleProvider ss;
    private long lastHeard;
    private long lastClapTime;


    public SoundDetection(SensorMode soundMode, float level, int gap) {
        timeGap = gap;
        ss = soundMode;
        threshold = level;
        if (!soundMode.getName().startsWith("Sound")) {
            throw new IllegalArgumentException("A Clap filter can only filter sound sensors");
        }
        lastHeard = -2 * timeGap;
        lastClapTime = 0;
    }


    public void fetchSample(float level[], int index) {
        level[index] = 0.0f;
        long now = System.currentTimeMillis();
        if (now - lastHeard > timeGap) {
            ss.fetchSample(level, index);
            if (level[index] >= threshold) {
                if (now - lastClapTime < timeGap) {
                    level[index] = 2.0f;
                } else {
                    level[index] = 1.0f;
                }
                lastClapTime = now;
                lastHeard = now;
            } else {
                level[index] = 0.0f;
            }
        }
    }


    public int sampleSize() {
        return 1;
    }
}





