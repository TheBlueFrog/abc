package sim;

import java.util.Random;

/**
 * Created by mike on 2/16/2016.
 *
 * When an agent can't move where it wants then we try
 * to follow an edge indicated by the obstacle
 */
public class WallFollowingBehavior extends TickingAgent {

    static final private String TAG = WallFollowingBehavior.class.getSimpleName();

    private final Displayable3DAgent mHost;
    private final Random mRandom;
    private int mMovesSinceTurn = 0;

    private double delta = Math.PI / 20.0;

    public WallFollowingBehavior(Displayable3DAgent host, double activationIntensity) {
        super(host.getSim(), activationIntensity);
        mHost = host;
        mRandom = new Random();
    }

    @Override
    public void tick(Simulation sim, Sensor sensor) {
        Location loc = mHost.getLocation();
        double heading = mHost.getHeading();
        loc.move (mHost.getHeading(), mHost.getVelocity());

        if (sim.isInside3DObject(loc)) {
            // turn left
            loc = mHost.getLocation();
            heading += (Math.PI / 4.0);
            loc.move(heading, mHost.getVelocity());

            if (sim.isInside3DObject(loc)) {
                // turn right
                loc = mHost.getLocation();
                heading -= (Math.PI / 2.0);
                loc.move(heading, mHost.getVelocity());

                if (sim.isInside3DObject(loc)) {
                    // turn around 180
                    heading = mHost.getHeading() - (Math.PI / 2.0);
                }
            }

            sensor.adjustHeading(this, heading);
            mActivationIntensity -= 0.2;
        }
    }

    @Override
    protected void consume(Message msg) {
        if (msg instanceof MoveFailedMsg) {
            // that's our queue
            mActivationIntensity += 1.0;
        }
    }
}
