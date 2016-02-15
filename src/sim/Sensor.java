package sim;

import java.awt.*;
import java.util.*;

/**
 * Created by mike on 2/14/2016.
 */
public class Sensor {

    static private class Location {
        private int mX;
        private int mY;

        public Location (int x, int y) {
            mX = x;
            mY = y;
        }
        public Location (Location l) {
            mX = l.mX;
            mY = l.mY;
        }

        public int getY() {
            return mY;
        }

        public int getX() {
            return mX;
        }

        private void move(double direction) {
            double dx = Math.cos(direction);
            double dy = Math.sin(direction);

            mX = (int) Math.round((double) mX + dx);
            mY = (int) Math.round((double) mY + dy);

            if (isUnknown())
                Main.simulation.discoverState(getX(), getY());

        }
        private boolean isUnknown() {
            return Main.simulation.getState (getX(), getY()) == Cell.Unknown;
        }

    }

    private Location mLocation;
    private double direction;

    public Sensor (Simulation sim, int x, int y) {

        if (sim.getState(x, y) == Cell.SensorFull)
            System.out.print("Can't start with sensor inside something");

        mLocation = new Location( x, y);
        this.direction = 0.0;

        sim.discoverState(mLocation.getX(), mLocation.getY());
    }

    public Color getColor(int x, int y) {
        return Color.pink;
    }
    public int getX() {
        return mLocation.getX();
    }
    public int getY() {
        return mLocation.getY();
    }

    private Random mRandom = new Random ();

    public void move() {
        Location n = new Location(mLocation);
        n.move (direction);

        // at least one direction has to work, where we came from

        while (somethingThere(n)) { // never true first time

            // can't go here, change direction try that
            direction += (Math.PI / 4.0) * (mRandom.nextInt(8));
            n = new Location(mLocation);
            n.move(direction);
        }

        mLocation = n;
    }

    private boolean somethingThere(Location n) {
        int s = Main.simulation.getState (n.getX(), n.getY());
        if (s == Cell.Unknown)
            return false; // don't know, assume nothing is there
        return s == Cell.SensorFull;
    }

}
