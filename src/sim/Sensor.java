package sim;


import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by mike on 2/14/2016.
 */
public class Sensor extends Displayable3DAgent {

    private static final String TAG = Sensor.class.getSimpleName();

    private List<Integer[]> mTrail = new ArrayList<>();

    public Sensor (Simulation sim, Location loc) {
        super(sim);
        mLocation = loc;

        sim.getFramework().register(this);

    }

    public Color getColor() {
        return Color.MAGENTA;
    }

    private Random mRandom = new Random ();
    private LidarData mLidar = null;

    /**
     * iterate the object one time-tick
     */
    public void tick() throws LocationInsideObjectException {

        mLidar = new LidarData (this, mHeading);

        if (mLidar.mRange[0] > 10.0) {
            mVelocity = Math.min(10.0, mVelocity * 1.04);
            Location dst = new Location(mLocation);
            dst.move(mHeading, mVelocity);
            setLocation(dst);
        }
        else {
            mVelocity = Math.min(10.0, mVelocity * 0.81);
            mHeading += (Math.PI / 180.0) * 10;
        }
    }

    /**
     * consume inter-agent message
     */
    @Override
    protected void consume(Message msg) {
        try {
            String s = (String) msg.mMessage;
            if (s.equals("tick"))
                tick();
            else
                mFramework.log(TAG, "Unknown message " + s);
        }
        catch (Exception | LocationInsideObjectException e) {
            mFramework.log(TAG,
                    String.format("consume() caught %s exception %s", e.getClass().getSimpleName(), e.getMessage()));
        }
    }

    @Override
    public void paint(Graphics2D g2, double real2PixelX, double real2PixelY) {

        Integer[] loc = new Integer[2];
        getPixelLocation (loc);

        showLidarData(g2, loc);

        // leave a trail behind us
        {
            mTrail.add(loc);
            if (mTrail.size() > 30)
                mTrail.remove(0);

            g2.setColor(getColor());
            for (Integer[] aa : mTrail)
                g2.fillRect(
                        aa[0],
                        aa[1],
                        (int) Math.round(real2PixelX),
                        (int) Math.round(real2PixelY));
        }

    }

    /**
     * draw the Lidar data as a bunch of wedges
     * @param g2
     * @param loc
     */
    private void showLidarData(Graphics2D g2, Integer[] loc) {
        double rpd = Math.PI / 180.0;
        int[] x = new int[3];
        int[] y = new int[3];
        x[0] = loc[0];
        y[0] = loc[1];
        x[1] = mSim.real2PixelX(getX() + (Math.cos((0 * rpd) + mHeading) * mLidar.mRange[0]));
        y[1] = mSim.real2PixelX(getY() + (Math.sin((0 * rpd) + mHeading) * mLidar.mRange[0]));

        g2.setColor(Color.pink);
        for (int i = 1; i < mLidar.mRange.length; i = i + 10) {
            x[2] = mSim.real2PixelX(getX() + (Math.cos((i * rpd) + mHeading) * mLidar.mRange[i]));
            y[2] = mSim.real2PixelX(getY() + (Math.sin((i * rpd) + mHeading) * mLidar.mRange[i]));
            g2.fillPolygon(x, y, 3);

            x[1] = x[2];    // save computing it again
            y[1] = y[2];
        }

        // close back to first point
        x[2] = mSim.real2PixelX(getX() + (Math.cos((0 * rpd) + mHeading) * mLidar.mRange[0]));
        y[2] = mSim.real2PixelX(getY() + (Math.sin((0 * rpd) + mHeading) * mLidar.mRange[0]));
        g2.fillPolygon(x, y, 3);
    }

    /**
     * return current location in pixels
     * @param loc
     */
    private void getPixelLocation(Integer[] loc) {
        loc[0] = mSim.real2PixelX(getX());
        loc[1] = mSim.real2PixelY(getY());
    }

    @Override
    public double distanceFrom(Location location, double direction) {
        return Double.POSITIVE_INFINITY;
    }

}
