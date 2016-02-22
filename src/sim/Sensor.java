package sim;


import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by mike on 2/14/2016.
 */
public class Sensor extends Displayable3DAgent {

    private static final String TAG = Sensor.class.getSimpleName();

    private List<TickingAgent> mAgents = new ArrayList<>();
    private List<Integer[]> mTrail = new ArrayList<>();

    public Sensor (Simulation sim, Location loc) {
        super(sim);
        mLocation = loc;

        sim.getFramework().register(this);

//        mAgents.add (new SensorHitWall(sim));
        mAgents.add(new SpiralBehavior(this, 1));
        mAgents.add(new RandomBehavior(this, -10));
        mAgents.add(new WallFollowingBehavior(this, 0));

        for (TickingAgent a : mAgents)
            a.start ();
    }

    public Color getColor() {
        return Color.pink;
    }

    private Random mRandom = new Random ();

    /**
     * iterate the object one time-tick
     */
    public void tick() throws LocationInsideObjectException {

        for (TickingAgent a : mAgents)
            a.tick(mSim, this);

        Location dst = new Location(mLocation);
        dst.move (mHeading, mVelocity);

        if (mSim.isInside3DObject(dst)) {
            // something there, don't update location
            for (TickingAgent a : mAgents)
                send(new Message(this, a.getClass(), new MoveFailedMsg(this, dst)));
        }
        else {
            // ok we can move there
            setLocation(dst);
        }

//        for (TickingAgent a : mAgents)
//            send(new Message(this, a.getClass(), this));
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
//        else if (s.equals("moved-no-obstacle")) {
//            if (mMovesSinceTurn > 40)
//                pickRandomHeading();
//        }
            else
                mFramework.log(TAG, "Unknown message " + s);
        }
        catch (Exception | LocationInsideObjectException e) {
            mFramework.log(TAG,
                    String.format("consume() caught %s exception %s", e.getClass().getSimpleName(), e.getMessage()));
        }
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

        Integer[] a = new Integer[2];
        a[0] = (int) Math.round(getX() * real2PixelX);
        a[1] = (int) Math.round(getY() * real2PixelY);
        mTrail.add(a);
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
