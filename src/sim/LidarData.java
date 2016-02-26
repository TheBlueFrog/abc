package sim;

/**
 * Created by mike on 2/22/2016.
 */
public class LidarData {

    private double mMaxRange = 50.0;

    public Location mLocation;
    public double mHeading;

    public double[] mRange = new double[360];

    public LidarData(Displayable3DAgent sensor) {
        // produce a sensor scan from the sensor's position and
        // heading

        mHeading = sensor.getHeading();
        mLocation = new Location(sensor.getLocation());

        double rpd = Math.PI / 180.0;
        Location loc = sensor.getLocation();
        for (int i = 0; i < 360; ++i) {
            double h = mHeading + (i * rpd);
            double d = sensor.getSim().distanceToObject (loc, h, mMaxRange);
            mRange[i] = d;
        }
    }
}
