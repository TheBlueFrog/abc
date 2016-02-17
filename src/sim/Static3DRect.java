package sim;

import java.awt.*;

/**
 * Created by mike on 2/16/16.
 */
public class Static3DRect extends Displayable3DAgent {

    private Polygon mPolygon = null;

    private final Location mUL;
    private final Location mUR;
    private final Location mLL;
    private final Location mLR;

    public Static3DRect(Simulation sim,
                        double ulx, double uly,
                        double urx, double ury,
                        double llx, double lly,
                        double lrx, double lry) {
        super(sim);

        mUL = new Location(ulx, uly);
        mUR = new Location(urx, ury);
        mLL = new Location(llx, lly);
        mLR = new Location(lrx, lry);
    }

    @Override
    public void paint(Graphics2D g2, double real2PixelX, double real2PixelY) {
        if (mPolygon == null) {
            int[] x = new int[4];
            int[] y = new int[4];

            x[0] = (int) Math.round(mUL.getX() * real2PixelX);
            x[1] = (int) Math.round(mUR.getX() * real2PixelX);
            x[2] = (int) Math.round(mLR.getX() * real2PixelX);
            x[3] = (int) Math.round(mLL.getX() * real2PixelX);

            y[0] = (int) Math.round(mUL.getY() * real2PixelY);
            y[1] = (int) Math.round(mUR.getY() * real2PixelY);
            y[2] = (int) Math.round(mLR.getY() * real2PixelY);
            y[3] = (int) Math.round(mLL.getY() * real2PixelY);

            mPolygon = new Polygon(x, y, 4);
        }

        g2.setColor(Color.gray);
        g2.fill(mPolygon);
    }

    @Override
    public boolean isInside(Simulation sim, Location loc) {
        return mPolygon.contains(sim.real2PixelX(loc.getX()), sim.real2PixelY(loc.getY()));
    }

    @Override
    protected void consume(Message msg) {
        // don't do much, just sit here
    }
}