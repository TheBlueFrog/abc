package sim;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by mike on 2/14/2016.
 */
public class Sensor extends Displayable3DAgent {

    private static final String TAG = Sensor.class.getSimpleName();

    static public int notActive = 0;
    static public int isActive = 1;

    private List<TickableAgent> mAgents = new ArrayList<>();

    public Sensor (Simulation sim, Location loc) {
        super(sim);
        mLocation = loc;

        sim.getFramework().register(this);

//        mAgents.add (new SensorHitWall(sim));
        mAgents.add(new SpiralBehavior(this, 1.0));
        mAgents.add(new RandomBehavior(this, 0.5));

        for (TickableAgent a : mAgents)
            a.start ();
    }

    public Color getColor() {
        return Color.pink;
    }

    private Random mRandom = new Random ();

    public void tick() {

        for (TickableAgent a : mAgents)
            a.tick(mSim, this);

        Location dst = new Location(mLocation);
        dst.move (mHeading, mVelocity);

        if (mSim.getSensorState(this, dst) == Sensor.isActive) {
            // something there, don't update location
        }
        else {
            // ok we can move there
            updateLocation (dst);
        }

//        for (TickableAgent a : mAgents)
//            send(new Message(this, a.getClass(), this));
    }

    @Override
    protected void consume(Message msg) {
        String s = (String) msg.mMessage;
        if (s.equals("tick"))
            tick();
//        else if (s.equals("moved-no-obstacle")) {
//            if (mMovesSinceTurn > 40)
//                pickRandomHeading();
//        }
        else
            mFramework.log(TAG, "Unknown message " + s);
    }

    private double tilt = 20.0;
    private double heading = 0.0;

//    public void pickRandomHeading() {
//        double dir = (Math.PI / 180.0) * (1 + mRandom.nextInt(359));
//
//        ////////////////HACK
///*        if (heading > 0.5) {
//            heading = 0.0;
//        }
//        else {
//            // going easterly, head west
//            heading = Math.PI;
//
//            tilt += (Math.PI / 180.0) * 10.0;
//        }
//
//        dir += heading + (Math.PI / 180.0) * tilt;
//*/
//        adjustHeading(this, dir);
//    }

    @Override
    public void paint(Graphics2D g2, double real2PixelX, double real2PixelY) {
        g2.setColor(getColor());
        g2.fillRect(
                (int) Math.round(getX() * real2PixelX),
                (int) Math.round(getY() * real2PixelY),
                (int) Math.round(real2PixelX),
                (int) Math.round(real2PixelY));
    }

}
