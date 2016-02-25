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


    abstract public void paint(Graphics2D g2, double real2PixelX, double real2PixelY);

    public boolean isInside(Simulation sim, Location loc) {
        // we're too small for anything to hit us...
        return false;
    }

    protected Location mLocation;
    protected double mHeading = 0.0;// 45.0 * (Math.PI / 180.0);
    protected double mVelocity = 1.0;

    public Displayable3DAgent(Simulation sim) {
        super(sim.getFramework());
        mSim = sim;
    }

    public Location getLocation() {
        return mLocation;
    }

    protected void setLocation(Location dst) throws LocationInsideObjectException {
        if (mSim.isInside3DObject(dst))
            throw new LocationInsideObjectException (String.format("%s attempted to move inside an object", getTag()));

        mLocation = dst;
    }


    public double getX() { return mLocation.getX(); }
    public double getY() { return mLocation.getY(); }
    public double getHeading() { return mHeading; }
    public double getVelocity() {
        return mVelocity;
    }

    /**
     * put x in the range 0..1, sigmoid
     * @param x
     * @return
     */
    private double normalize(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    public Simulation getSim() {
        return mSim;
    }


    /**
     * return the distance from a point, looking in a given direction
     * to the ojbect.  if we don't intersect that line return infinity
     *
     * @param location
     * @param direction
     * @return
     */
    public abstract double distanceFrom(Location location, double direction);
}
