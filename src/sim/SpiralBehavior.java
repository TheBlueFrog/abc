package sim;

/**
 * Created by mike on 2/16/2016.
 */
public class SpiralBehavior extends TickableAgent {

    static final private String TAG = SpiralBehavior.class.getSimpleName();

    private final Displayable3DAgent mHost;

    private int mNumTicksToTurn = 6;
    private double mDegPerTurn = 360 / 20;
    private double mTurn = (Math.PI / 180.0) * mDegPerTurn;
    private int mNumTurns = 0;

    private int mMovesSinceTurn = 0;


    public SpiralBehavior(Displayable3DAgent host, double activationIntensity) {
        super(host.getSim(), activationIntensity);
        mHost = host;
    }

    @Override
    public void tick(Simulation sim, Sensor sensor) {
        if (++mMovesSinceTurn > mNumTicksToTurn) {
            sensor.adjustHeading(this, mTurn);
            mMovesSinceTurn = 0;

            // every full circle widen the circle
            if ((++mNumTurns * mDegPerTurn) > (360.0 - mDegPerTurn)) {
                ++mNumTicksToTurn;
                mNumTurns = 0;
            }
        }
    }

    @Override
    protected void consume(Message msg) {
    }
}
