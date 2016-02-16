package sim;

import javafx.util.Duration;

import java.awt.*;
import java.util.*;

/**
 * Created by mike on 2/14/2016.
 */
public class Sensor extends Agent {

    private Location mLocation;
    private double direction;

    public Sensor (Simulation sim, int x, int y) {
        super(sim.getFramework());

//        if (sim.getState(x, y) == Cell.SensorFull)
//            System.out.print("Can't start with sensor inside something");

        mLocation = new Location(x, y);
        this.direction = 0.0;

        sim.discoverState(mLocation);

        sim.getFramework().register(this);
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

    public void move(Simulation simulation) {
        Location n = simulation.move(mLocation, direction);

        // at least one direction has to work, where we came from

        while (somethingThere(simulation, n)) { // never true first time

            // can't go here, change direction try that
            direction += (Math.PI / 4.0) * (mRandom.nextInt(8));
            n = simulation.move(mLocation, direction);
        }

        mLocation = n;
    }

    private boolean somethingThere(Simulation simulation, Location n) {
        Cell c = simulation.getDiscoveredCell (n.getX(), n.getY());
        if (c == null)
            return false;
        return c.getState() == Cell.SensorFull;
    }

    @Override
    protected void consume(Message msg) {
        String s = (String) msg.mMessage;
        move(Main.simulation);
    }

}
