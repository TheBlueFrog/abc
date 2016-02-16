package sim;

import java.awt.*;
import java.util.*;

/**
 * Created by mike on 2/14/2016.
 */
public class Sensor extends Agent {

    private static final String TAG = Sensor.class.getSimpleName();

    private Location mLocation;
    private SensorHeading mHeading;
    private SensorHitWall mHitWall;

    public Sensor (Simulation sim, int x, int y) {
        super(sim.getFramework());

//        if (sim.getState(x, y) == Cell.SensorFull)
//            System.out.print("Can't start with sensor inside something");

        mLocation = new Location(x, y);

        sim.discoverState(mLocation);

        sim.getFramework().register(this);

        mHitWall = new SensorHitWall(sim);
        mHitWall.start ();

        mHeading = new SensorHeading(sim.getFramework());
        mHeading.start();
    }

    public Color getColor(int x, int y) {
        return Color.pink;
    }
    public int getX() {
        return (int) mLocation.getX();
    }
    public int getY() {
        return (int) mLocation.getY();
    }

    private Random mRandom = new Random ();

    private int mMovesSinceTurn = 0;

    public void move(Simulation simulation) {

        Location dst = new Location(mLocation);
        dst.move (mHeading.getHeading());
        simulation.discoverState(dst);

        if (simulation.getState(dst) == Cell.SensorFull) {
            // something there, don't move
            send(new Message(this, mHitWall.getClass(), this));
        }
        else {
            // ok we can move there
            if (mMovesSinceTurn > 20) {
                send(new Message(this, this.getClass(), "bored"));
            }
            else {
                mLocation = dst;
                mMovesSinceTurn++;
            }
        }
    }

    private boolean somethingThere(Simulation simulation, Location n) {
        Cell c = simulation.getDiscoveredCell ((int) n.getX(), (int) n.getY());
        if (c == null)
            return false;
        return c.getState() == Cell.SensorFull;
    }

    @Override
    protected void consume(Message msg) {
        String s = (String) msg.mMessage;
        if (s.equals("move"))
            move(Main.simulation);
        else if (s.equals("bored"))
            pickRandomHeading();
        else
            mFramework.log(TAG, "Unknown message " + s);
    }

    public double getHeading() {
        return mHeading.getHeading();
    }

    public void setHeading(double h) {
        this.mHeading.setHeading(h);
        mMovesSinceTurn = 0;
    }

    public void pickRandomHeading() {
        double dir = getHeading();
        dir = (Math.PI / 4.0) * (1 + mRandom.nextInt(7));
        setHeading(dir);
    }
}
