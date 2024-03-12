import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class SoundDetection implements SampleProvider {
    private final float threshold;
    private final int dotTimeGap;
    private final int dashTimeGap;
    private final SampleProvider ss;
    private long lastHeard;

    public SoundDetection(SensorMode soundMode, float level, int dotGap, int dashGap) {
        dotTimeGap = dotGap;
        dashTimeGap = dashGap;
        ss = soundMode;
        threshold = level;
        if (!soundMode.getName().startsWith("Sound")) {
            throw new IllegalArgumentException("A Clap filter can only filter sound sensors");
        }
        lastHeard = -2 * dotGap; // Assuming it starts with a dot
    }

    public void fetchSample(float level[], int index) {
        level[index] = 0.0f;
        long now = System.currentTimeMillis();
        if (now - lastHeard > dotTimeGap) {
            ss.fetchSample(level, index);
            if (level[index] >= threshold) {
                long timeSinceLastClap = now - lastHeard;
                if (timeSinceLastClap < dashTimeGap) {
                    // Double clap (dash)
                    level[index] = 2.0f;
                    lastHeard += dashTimeGap; // Move the lastHeard time by dashTimeGap
                } else {
                    // Single clap (dot)
                    level[index] = 1.0f;
                    lastHeard = now;
                }
            }
        }
    }

    public int sampleSize() {
        return 1;
    }
}



