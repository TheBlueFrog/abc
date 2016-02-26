package sim;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mike on 2/14/2016.
 */
public class Simulation {
    private Sensor mSensor;
    private Framework mFramework;
    private List<Displayable3DAgent> mReality = new ArrayList<>();
    double maxX;
    double maxY;
    private final double mReal2PixelX;
    private final double mReal2PixelY;
    private Framework framework;

    void step() {
        mFramework.send(new Message(null, mSensor.getClass(), "tick"));
    }

    public Simulation(double maxX, double maxY, double real2PixelX, double real2PixelY) {

        mFramework = new Framework();

        this.maxX = maxX;
        this.maxY = maxY;
        this.mReal2PixelX = real2PixelX;
        this.mReal2PixelY = real2PixelY;

//        mDiscovered = new Cell[maxX][maxY];

        // setup reality, all coords are in [0..maxX]

        // left, right, top, bottom
        mReality.add(new Static3DWall(this,     0,    0,     0, maxY));
        mReality.add(new Static3DWall(this,     0,    0,  maxX,    0));
        mReality.add(new Static3DWall(this,  maxX,    0,  maxX, maxX));
        mReality.add(new Static3DWall(this,     0, maxY,  maxX, maxY));

        double x = maxX / 20;
        double y = maxY / 20;
        Random r = new Random();

        for (int i = 0; i < 25; ++i)
            addRect (x * (r.nextInt(17) - 3), y * (r.nextInt(17) - 3), r.nextInt(17) + 2, r.nextInt(17) + 2);


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

        mSensor = new Sensor (this, new Location(20, maxY / 3));
        mSensor.start();
    }

    private void addRect(double x, double y, double w, double h) {
        mReality.add(new Static3DWall(this, x, y,  x + w, y));
        mReality.add(new Static3DWall(this, x, y,  x, y + h));
        mReality.add(new Static3DWall(this, x + w, y + h,  x + w, y));
        mReality.add(new Static3DWall(this, x + w, y + h,  x, y + h));
    }

//    public int getState(Location loc) {
//        return getState((int)loc.getX(), (int)loc.getY());
//    }
//    public int getState(int x, int y) {
//        return mDiscovered[x][y].getState();
//    }

    public void onClick(int x, int y) {
    }

    public Sensor getSensor() {
        return mSensor;
    }

//    public void discoverState(Location loc) {
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
//        g2.fillRect(0, 0, (int) Math.round(maxX * mReal2PixelX), (int) Math.round(maxY * mReal2PixelY));

        // render the static agents
        for (Displayable3DAgent a : mReality)
            a.paint(g2, mReal2PixelX, mReal2PixelY);

        // render the sensor
        mSensor.paint(g2, mReal2PixelX, mReal2PixelY);
    }

    public boolean isInside3DObject(Location loc) {
        for (Displayable3DAgent a : mReality)
            if (a.isInside(this, loc))
                return true;

        return false;
    }

    public double getReal2PixelX() {
        return mReal2PixelX;
    }
    public double getReal2PixelY() {
        return mReal2PixelY;
    }

    public int real2PixelX (double x) {
        return (int) Math.round(mReal2PixelX * x);
    }
    public int real2PixelY (double x) {
        return (int) Math.round(mReal2PixelY * x);
    }

    /**
     * given a location and a direction find distance to nearest
     * object
     * @param location
     * @param direction
     * @param maxRange upper limit on returned range
     */
    public double distanceToObject(Location location, double direction, double maxRange) {
        double range = maxRange;
        for (Displayable3DAgent a : mReality) {
            double d = a.distanceFrom(location, direction);
            if (d < range)
                range = d;
        }

        return range;
    }
}
