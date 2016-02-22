package sim;

import java.util.Random;

/**
 * Created by mike on 2/15/2016.
 *
 * notified when the sensor can't go where it's heading, do something
 */
public class SensorHitWall extends TickingAgent {

    private static final String TAG = SensorHitWall.class.getSimpleName();

    private Random mRandom;

    public SensorHitWall(Simulation sim) {
        super(sim, 0);

        mRandom = new Random();
    }

    @Override
    public void tick(Simulation sim, Sensor sensor) {
        if (sim.isInside3DObject(sensor.getLocation())) {
            //
//            sensor.pickRandomHeading();
        }
    }

    @Override
    protected void consume(Message msg) {
        mFramework.log(TAG, "Unknown message " + msg.mMessage);
    }
}
