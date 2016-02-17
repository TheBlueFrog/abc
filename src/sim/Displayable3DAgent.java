package sim;

import java.awt.*;

/**
 * Created by mike on 2/16/16.
 *
 * displayable objects in reality
 */
public abstract class Displayable3DAgent extends Agent {

    private static String TAG = "";
    protected final Simulation mSim;

    protected void updateLocation(Location dst) {
        mLocation = dst;
        mMovesSinceTurn++;

    }

    abstract public void paint(Graphics2D g2, double real2PixelX, double real2PixelY);

    public boolean isInside(Simulation sim, Location loc) {
        // we're too small for anything to hit us...
        return false;
    }

    protected Location mLocation;
    protected double mHeading = 0.0; // east
    protected double mVelocity = 1.0;

    protected int mMovesSinceTurn = 0;

    public Displayable3DAgent(Simulation sim) {
        super(sim.getFramework());
        mSim = sim;
    }

    public Location getLocation() {
        return mLocation;
    }

    public double getX() { return mLocation.getX(); }
    public double getY() { return mLocation.getY(); }

    public double getHeading() { return mHeading; }

    /**
     * adjust the heading of the agent
     * @param agent
     * @param h amount to adjust heading by
     */
    public void adjustHeading(TickableAgent agent, double h) {

        mHeading += h * agent.mActivationIntensity;

        mMovesSinceTurn = 0;

        mFramework.log(TAG, String.format("adjustHeading: %s adjusted heading, now %2.5f", agent.getTag(), mHeading));
    }

    public Simulation getSim() {
        return mSim;
    }
}
