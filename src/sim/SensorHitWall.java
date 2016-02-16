package sim;

import java.util.Random;

/**
 * Created by mike on 2/15/2016.
 *
 * notified when the sensor can't go where it's heading, do something
 */
public class SensorHitWall extends Agent {

    private Random mRandom;

    public SensorHitWall(Simulation sim) {
        super(sim.getFramework());

        mRandom = new Random();
    }

    @Override
    protected void consume(Message msg) {
        Sensor sensor = (Sensor) msg.mMessage;

        sensor.pickRandomHeading();
    }
}
