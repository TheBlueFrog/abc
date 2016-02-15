package sim;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mike on 2/14/2016.
 */
public class Simulation {
    private final Sensor mSensor;
    private Cell[][] theTruth;
    private Cell[][] explored;
    int maxcolumns;
    int maxrows;

    static private Map<Integer, Color> mColors = new HashMap<Integer, Color>();
    static {
        mColors.put(Cell.Unknown, Color.white);
        mColors.put(Cell.SensorEmpty, Color.lightGray);
        mColors.put(Cell.SensorFull, Color.black);

    };

    void step() {
        mSensor.move ();
    }

    public Simulation(int maxcolumns, int maxrows) {
        this.maxcolumns = maxcolumns;
        this.maxrows = maxrows;
        theTruth= new Cell[maxcolumns][maxrows];
        explored = new Cell[maxcolumns][maxrows];

        for (int x = 0; x < maxcolumns; ++x) {
            for (int y = 0; y < maxrows; ++y) {
                explored[x][y] = new Cell(Cell.Unknown);
                theTruth[x][y] = new Cell(Cell.SensorEmpty);
            }
        }

        for (int x = 0; x < maxcolumns; ++x) {
            theTruth[x][0].setState(Cell.SensorFull);
            theTruth[x][maxrows - 1].setState(Cell.SensorFull);
        }
        for (int y = 0; y < maxrows; ++y) {
            theTruth[0][y].setState(Cell.SensorFull);
            theTruth[maxcolumns - 1][y].setState(Cell.SensorFull);
        }

        mSensor = new Sensor (this, maxcolumns / 2, maxrows / 2);
    }


    public int getState(int x, int y) {
        return explored[x][y].getState();
    }

    public Color getColor(int x, int y) {
        return mColors.get (getState(x, y));
    }

    public void onClick(int x, int y) {
    }

    public Sensor getSensor() {
        return mSensor;
    }

    public void discoverState(int x, int y) {
        int t = theTruth[x][y].getState();
        if (t == Cell.Unknown)
            System.out.print("oops");

        explored[x][y].setState(t);
    }
}
