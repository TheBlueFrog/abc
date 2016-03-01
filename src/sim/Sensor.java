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

    private Random mRandom = new Random ();
    private List<LidarData> mLidar = null;

    public Sensor (Simulation sim, WorldLocation loc) {
        super(sim);
        mLocation = loc;

        sim.getFramework().register(this);

        mLidar = new ArrayList<>();
        mLidar.add(new LidarData(this));
    }

    public Color getColor() {
        return Color.MAGENTA;
    }

    /**
     * iterate the object one time-tick
     */
    public void tick() throws LocationInsideObjectException {

        mSim.log(TAG, "Not implemented");
//        mLidar = new LidarData (this, mHeading);
//
////        double d = mLidar.mRange[0] + mLidar.mRange[1] + mLidar.mRange[359] + mLidar.mRange[358] / 4;
//
//        if (mLidar.mRange[0] > 10.0) {
//            mVelocity = Math.min(10.0, mVelocity * 1.04);
//            WorldLocation dst = new WorldLocation(mLocation);
//            dst.move(mHeading, mVelocity);
//            setLocation(dst);
//        }
//        else {
//            mVelocity = Math.min(10.0, mVelocity * 0.81);
//            mHeading += (Math.PI / 180.0) * 10;
//        }
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

        showLidarData(g2, false);

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

    private List<Integer> mHits = new ArrayList<>();

    /**
     * draw the Lidar data as a bunch of wedges
     * @param g2
     * @param showScans
     *
     */
    private void showLidarData(Graphics2D g2, boolean showScans) {

        if (showScans)
            for (LidarData d : mLidar)
                d.paint(g2, mSim);


//        int[] x = new int[3];
//        int[] y = new int[3];
//        x[0] = loc[0];
//        y[0] = loc[1];
//        x[1] = mSim.world2PixelX(getX() + (Math.cos((0 * rpd) + mHeading) * lidar.mRange[0]));
//        y[1] = mSim.world2PixelX(getY() + (Math.sin((0 * rpd) + mHeading) * lidar.mRange[0]));
//
        g2.setColor(Color.BLUE);
        for (int i = 0; i < mHits.size(); i += 2) {
            g2.fillOval(mHits.get(i), mHits.get(i+1), 2, 2);
//
//            x[1] = x[2];    // save computing it again
//            y[1] = y[2];
        }
//
//        // close back to first point
//        x[2] = mSim.world2PixelX(getX() + (Math.cos((0 * rpd) + mHeading) * lidar.mRange[0]));
//        y[2] = mSim.world2PixelX(getY() + (Math.sin((0 * rpd) + mHeading) * lidar.mRange[0]));
//        g2.fillPolygon(x, y, 3);
    }

    /**
     * return current location in pixels
     * @param loc
     */
    private void getPixelLocation(Integer[] loc) {
        loc[0] = mSim.world2PixelX(getX());
        loc[1] = mSim.world2PixelY(getY());
    }

    @Override
    public double distanceFrom(WorldLocation location, double direction) {
        return Double.POSITIVE_INFINITY;
    }

    public void scan(WorldLocation loc) {
        scan(loc.getX(), loc.getY());
    }
    public void scan(double x, double y) {
        mLocation = new WorldLocation(x, y);
        LidarData d = new LidarData(this);
        mLidar.add(d);

        double rpd = Math.PI / 180.0;
        for (int i = 0; i < d.mRange.length; ++i) {
            if (d.mRange[i] < d.getMaxRange()) {
                double h = i * rpd;
                double x1 = d.mLocation.getX() + (Math.cos(mHeading + h) * d.mRange[i]);
                double y1 = d.mLocation.getY() + (Math.sin(mHeading + h) * d.mRange[i]);
                mHits.add(mSim.world2PixelX(x1));
                mHits.add(mSim.world2PixelY(y1));
            }
        }
    }

    public LidarData getCurScan() {
        return mLidar.get(mLidar.size() - 1);
    }

    /**
     * @param r
     * @return number of scans that were taken in this rectangle
     * note Rectangles are always screen coords not world
     */
    public int sample(Rectangle r) {
        int count = 0;
        for (LidarData s : mLidar)
            if (r.contains(mSim.world2PixelX(s.mLocation.getX()), mSim.world2PixelX(s.mLocation.getY())))
                count++;

        return count;
    }

}
