package sim;

import java.util.Random;

/**
 * Created by mike on 2/16/2016.
 */
public class RandomBehavior extends TickingAgent {

    static final private String TAG = RandomBehavior.class.getSimpleName();

    private final Displayable3DAgent mHost;
    private final Random mRandom;
    private int mMovesSinceTurn = 0;

    private double delta = Math.PI / 20.0;

    public RandomBehavior(Displayable3DAgent host, double activationIntensity) {
        super(host.getSim(), activationIntensity);
        mHost = host;
        mRandom = new Random();
    }

    @Override
    public void tick(Simulation sim, Sensor sensor) {
        if (++mMovesSinceTurn > mRandom.nextInt(100)) {
            sensor.adjustHeading(this, (mRandom.nextInt(3) - 2) * delta);
            mMovesSinceTurn = 0;
        }
    }

    @Override
    protected void consume(Message msg) {
        if (msg instanceof MoveFailedMsg) {
            mActivationIntensity += 1.0;
        }
    }
}
