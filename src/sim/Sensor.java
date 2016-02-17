package sim;

import java.awt.*;
import java.util.*;

/**
 * Created by mike on 2/14/2016.
 */
public class Sensor extends Displayable3DAgent {

    private static final String TAG = Sensor.class.getSimpleName();

    static public int notActive = 0;
    static public int isActive = 1;

    private Location mLocation;
    private SensorHeading mHeading;
    private SensorHitWall mHitWall;

    public Sensor (Simulation sim, Location loc) {
        super(sim);
        mLocation = loc;

        sim.getFramework().register(this);

        mHitWall = new SensorHitWall(sim);
        mHitWall.start ();

        mHeading = new SensorHeading(sim.getFramework());
        mHeading.start();
    }

    public Color getColor() {
        return Color.pink;
    }

    public double getX() { return mLocation.getX(); }
    public double getY() {
        return mLocation.getY();
    }

    private Random mRandom = new Random ();

    private int mMovesSinceTurn = 0;

    public void move(Simulation simulation) {

        Location dst = new Location(mLocation);
        dst.move (mHeading.getHeading(), 1.0);

        if (simulation.getSensorState(this, dst) == Sensor.isActive) {
            // something there, don't move
            send(new Message(this, mHitWall.getClass(), this));
        }
        else {
            // ok we can move there
            if (mMovesSinceTurn > 40) {
                send(new Message(this, this.getClass(), "bored"));
            }
            else {
                mLocation = dst;
                mMovesSinceTurn++;
            }
        }
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

        mFramework.log(TAG, String.format("heading now %2.5f", h));
    }

    private double tilt = 20.0;
    private double heading = 0.0;

    public void pickRandomHeading() {
        double dir = (Math.PI / 180.0) * (1 + mRandom.nextInt(359));

        ////////////////HACK
/*        if (heading > 0.5) {
            heading = 0.0;
        }
        else {
            // going easterly, head west
            heading = Math.PI;

            tilt += (Math.PI / 180.0) * 10.0;
        }

        dir += heading + (Math.PI / 180.0) * tilt;
*/
        setHeading(dir);
    }

    @Override
    public void paint(Graphics2D g2, double real2PixelX, double real2PixelY) {
        g2.setColor(getColor());
        g2.fillRect(
                (int) Math.round(getX() * real2PixelX),
                (int) Math.round(getY() * real2PixelY),
                (int) Math.round(real2PixelX),
                (int) Math.round(real2PixelY));
    }

    @Override
    public boolean isInside(Simulation sim, Location loc) {
        // we're too small for anything to hit us...
        return false;
    }
}
