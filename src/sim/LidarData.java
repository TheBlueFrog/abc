package sim;

import java.awt.*;

/**
 * Created by mike on 2/22/2016.
 */
public class LidarData {

    private double mMaxRange = 100.0;

    public WorldLocation mLocation;
    public double mHeading;

    public double[] mRange = new double[360];
    public Polygon mShape = null;

    public LidarData(Displayable3DAgent sensor) {
        // produce a sensor scan from the sensor's position and
        // heading

        mHeading = sensor.getHeading();
        mLocation = new WorldLocation(sensor.getLocation());

        double rpd = Math.PI / 180.0;
        WorldLocation loc = sensor.getLocation();
        for (int i = 0; i < 360; ++i) {
            double h = mHeading + (i * rpd);
            double d = sensor.getSim().distanceToObject (loc, h, mMaxRange);
            mRange[i] = d;
        }
    }

    public double getMaxRange() {
        return mMaxRange;
    }

    public void paint(Graphics g2, Simulation sim) {
        if (mShape == null) {
            double rpd = Math.PI / 180.0;

            int[] x = new int[361];
            int[] y = new int[361];

            for (int i = 0; i < mRange.length; i = i + 1) {
                x[i] = sim.world2PixelX(mLocation.getX() + (Math.cos((i * rpd) + mHeading) * mRange[i]));
                y[i] = sim.world2PixelX(mLocation.getY() + (Math.sin((i * rpd) + mHeading) * mRange[i]));
            }

            // close back to first point
            x[360] = sim.world2PixelX(mLocation.getX() + (Math.cos((0 * rpd) + mHeading) * mRange[0]));
            y[360] = sim.world2PixelX(mLocation.getY() + (Math.sin((0 * rpd) + mHeading) * mRange[0]));

            mShape = new Polygon(x, y, 361);
        }

        g2.setColor(Color.pink);
        g2.drawPolygon(mShape);
    }
}
