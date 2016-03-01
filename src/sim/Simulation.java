package sim;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by mike on 2/14/2016.
 */
public class Simulation {
    private Sensor mSensor;
    private Framework mFramework;
    private List<Displayable3DAgent> mReality = new ArrayList<>();
    double maxX;
    double maxY;
    private final double mWorld2PixelX;
    private final double mWorld2PixelY;
    private Framework framework;
    private Random mRandom = new Random();


    void step() {
//        mFramework.send(new Message(null, mSensor.getClass(), "tick"));
    }

    public Simulation(double maxX, double maxY, double real2PixelX, double real2PixelY) {

        mFramework = new Framework();

        this.maxX = maxX;
        this.maxY = maxY;
        this.mWorld2PixelX = real2PixelX;
        this.mWorld2PixelY = real2PixelY;

//        mDiscovered = new Cell[maxX][maxY];

        // setup reality, all coords are in [0..maxX]

        // left, right, top, bottom
        mReality.add(new Static3DWall(this,     0,    0,     0, maxY));
        mReality.add(new Static3DWall(this,     0,    0,  maxX,    0));
        mReality.add(new Static3DWall(this,  maxX,    0,  maxX, maxX));
        mReality.add(new Static3DWall(this,     0, maxY,  maxX, maxY));

        double x = maxX / 20;
        double y = maxY / 20;

        for (int i = 0; i < 45; ++i)
            addRect (mRandom.nextDouble() * 20 * x, mRandom.nextDouble() * 20 * y,
                    mRandom.nextDouble() * 20, mRandom.nextDouble() * 20);


//        for (int x = 0; x < maxX; ++x) {
//            mReality[x][0].setState(Cell.SensorFull);
//            mReality[x][maxY - 1].setState(Cell.SensorFull);
//        }
//        for (int y = 0; y < maxY; ++y) {
//            mReality[0][y].setState(Cell.SensorFull);
//            mReality[maxX - 1][y].setState(Cell.SensorFull);
//        }

        // setup what has been discovered
//        mDiscovered[x][y] = new Cell(Cell.SensorEmpty);

        mSensor = new Sensor (this, new WorldLocation(20, maxY / 3));
        mSensor.start();
    }

    private void addRect(double x, double y, double w, double h) {
        mReality.add(new Static3DWall(this, x, y,  x + w, y));
        mReality.add(new Static3DWall(this, x, y,  x, y + h));
        mReality.add(new Static3DWall(this, x + w, y + h,  x + w, y));
        mReality.add(new Static3DWall(this, x + w, y + h,  x, y + h));
    }

//    public int getState(WorldLocation loc) {
//        return getState((int)loc.getX(), (int)loc.getY());
//    }
//    public int getState(int x, int y) {
//        return mDiscovered[x][y].getState();
//    }

    public void onClick(WorldLocation location) {
//        mSensor.scan (x, y);

        WorldLocation loc = calcMissingData ();
        mSensor.scan(loc);
    }

    private WorldLocation calcMissingData() {

        List<Rectangle> sparseness = sampleQuadrants(new Rectangle(0, 0, world2PixelX(maxX), world2PixelY(maxY)));

        Rectangle r = sparseness.get(0); // most sparse

        return new WorldLocation(
                pixel2WorldX(r.x + mRandom.nextInt(r.width)),
                pixel2WorldY(r.y + mRandom.nextInt(r.height)));
    }

    /**
     * @param src
     * @return ordered set of quadrants of the input rectangle, first is
     * least sampled, last is most sampled
     */
    private List<Rectangle> sampleQuadrants(Rectangle src) {

        // make quadrants
        List<Rectangle> r = new ArrayList<>(4);
        int w = src.width / 2;
        int h = src.height / 2;
        r.add(new Rectangle(0, 0, w, h));
        r.add(new Rectangle(w, 0, w, h));
        r.add(new Rectangle(0, h, w, h));
        r.add(new Rectangle(w, h, w, h));

        // sample each, put into sorted map
        Map<Integer, List<Rectangle>> sr = new TreeMap<>();
        for (Rectangle i : r) {
            int c = sample(i);
            if ( ! sr.containsKey(c))
                sr.put(c, new ArrayList<Rectangle>());
            sr.get(c).add(i);
        }
        // extract rectangles
        List<Rectangle> a = new ArrayList<>();
        for (Integer i : sr.keySet())
            a.addAll(sr.get(i));

        return a;
    }

    private Integer sample(Rectangle r) {
        int count = mSensor.sample(r);
        return count;
    }

    public Sensor getSensor() {
        return mSensor;
    }

//    public void discoverState(WorldLocation loc) {
//        discoverState((int)loc.getX(), (int)loc.getY());
//    }
//    public void discoverState(int x, int y) {
////        if (mDiscovered[x][y] == null)
////            mDiscovered[x][y] = mReality[x][y];
//    }
//
//    public boolean isUnknown (int x, int y) {
//        return mDiscovered[x][y] == null;
//    }
//    public Cell getDiscoveredCell(int x, int y) {
//        return mDiscovered[x][y];
//    }

    public Framework getFramework() {
        return mFramework;
    }


    public void paint(Graphics2D g2) {

        // set background
//        g2.setColor(Color.white);
//        g2.fillRect(0, 0, (int) Math.round(maxX * mWorld2PixelX), (int) Math.round(maxY * mWorld2PixelY));

        // render the static agents
        for (Displayable3DAgent a : mReality)
            a.paint(g2, mWorld2PixelX, mWorld2PixelY);

        // render the sensor
        mSensor.paint(g2, mWorld2PixelX, mWorld2PixelY);
    }

    public boolean isInside3DObject(WorldLocation loc) {
        for (Displayable3DAgent a : mReality)
            if (a.isInside(this, loc))
                return true;

        return false;
    }

    public double getWorld2PixelX() {
        return mWorld2PixelX;
    }
    public double getWorld2PixelY() {
        return mWorld2PixelY;
    }

    public int world2PixelX(double x) {
        return (int) Math.round(mWorld2PixelX * x);
    }
    public int world2PixelY(double x) {
        return (int) Math.round(mWorld2PixelY * x);
    }
    public double pixel2WorldX(int x) {
        return x / mWorld2PixelX;
    }
    public double pixel2WorldY(double x) {
        return x / mWorld2PixelY;
    }

    /**
     * given a location and a direction find distance to nearest
     * object
     * @param location
     * @param direction
     * @param maxRange upper limit on returned range
     */
    public double distanceToObject(WorldLocation location, double direction, double maxRange) {
        double range = maxRange;
        for (Displayable3DAgent a : mReality) {
            double d = a.distanceFrom(location, direction);
            if (d < range)
                range = d;
        }

        return range;
    }

    public void log(String tag, String s) {
        mFramework.log(tag, s);
    }
}
