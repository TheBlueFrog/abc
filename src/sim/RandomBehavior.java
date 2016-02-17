package sim;

import java.util.Random;

/**
 * Created by mike on 2/16/2016.
 */
public class RandomBehavior extends TickableAgent {

    static final private String TAG = RandomBehavior.class.getSimpleName();

    private final Displayable3DAgent mHost;
    private final Random mRandom;

    public RandomBehavior(Displayable3DAgent host, double activationIntensity) {
        super(host.getSim(), activationIntensity);
        mHost = host;
        mRandom = new Random();
    }

    @Override
    public void tick(Simulation sim, Sensor sensor) {
        if (mHost.mMovesSinceTurn > mRandom.nextInt(100)) {
            sensor.adjustHeading(this, Math.PI / 8.0);
        }
    }

    @Override
    protected void consume(Message msg) {

    }
}
