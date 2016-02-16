package sim;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mike on 2/14/2016.
 */
public class Simulation {
    private Sensor mSensor;
    private Framework mFramework;
    private Cell[][] mReality;
    private Cell[][] mDiscovered;
    int maxcolumns;
    int maxrows;

    static private Map<Integer, Color> mColors = new HashMap<Integer, Color>();
    static {
        mColors.put(Cell.SensorEmpty, Color.lightGray);
        mColors.put(Cell.SensorFull, Color.black);

    };

    void step() {
        mFramework.send(new Message(null, mSensor.getClass(), "move"));
    }

    public Simulation(int maxcolumns, int maxrows) {

        mFramework = new Framework();

        this.maxcolumns = maxcolumns;
        this.maxrows = maxrows;
        mReality = new Cell[maxcolumns][maxrows];
        mDiscovered = new Cell[maxcolumns][maxrows];

        // setup reality
        for (int x = 0; x < maxcolumns; ++x) {
            for (int y = 0; y < maxrows; ++y) {
                mReality[x][y] = new Cell();
            }
        }
        for (int x = 0; x < maxcolumns; ++x) {
            mReality[x][0].setState(Cell.SensorFull);
            mReality[x][maxrows - 1].setState(Cell.SensorFull);
        }
        for (int y = 0; y < maxrows; ++y) {
            mReality[0][y].setState(Cell.SensorFull);
            mReality[maxcolumns - 1][y].setState(Cell.SensorFull);
        }

        // setup what has been discovered
//        mDiscovered[x][y] = new Cell(Cell.SensorEmpty);

        mSensor = new Sensor (this, maxcolumns / 2, maxrows / 2);
        mSensor.start();
    }


    public int getState(Location loc) {
        return getState(loc.getX(), loc.getY());
    }
    public int getState(int x, int y) {
        return mDiscovered[x][y].getState();
    }

    public Color getColor(int x, int y) {
        if (mDiscovered[x][y] != null)
            return mColors.get (getState(x, y));
        else
            return Color.white;
    }

    public void onClick(int x, int y) {
    }

    public Sensor getSensor() {
        return mSensor;
    }

    public void discoverState(Location loc) {
        discoverState(loc.getX(), loc.getY());
    }
    public void discoverState(int x, int y) {
        if (mDiscovered[x][y] == null)
            mDiscovered[x][y] = mReality[x][y];
    }

    public boolean isUnknown (int x, int y) {
        return mDiscovered[x][y] == null;
    }
    public Cell getDiscoveredCell(int x, int y) {
        return mDiscovered[x][y];
    }

    public Framework getFramework() {
        return mFramework;
    }


}
