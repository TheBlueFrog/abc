package sim;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by mike on 2/16/16.
 */
public class Static3DWall extends Displayable3DAgent {

    private final WorldLocation mA;
    private final WorldLocation mB;
    private Line2D mLine;

    public Static3DWall(Simulation sim, double x, double y, double x1, double y1) {
        super(sim);

        mA = new WorldLocation(x, y);
        mB = new WorldLocation(x1, y1);
    }
    public Static3DWall(Simulation sim, WorldLocation a, WorldLocation b) {
        super(sim);

        mA = new WorldLocation(a);
        mB = new WorldLocation(b);
    }


    @Override
    public void paint(Graphics2D g2, double real2PixelX, double real2PixelY) {
        if (mLine == null) {

            mLine = new Line2D.Double(
                    (int) Math.round(mA.getX() * real2PixelX),
                    (int) Math.round(mA.getY() * real2PixelY),
                    (int) Math.round(mB.getX() * real2PixelX),
                    (int) Math.round(mB.getY() * real2PixelY));
        }

        g2.setColor(Color.gray);
        g2.draw(mLine);
    }

    @Override
    public boolean isInside(Simulation sim, WorldLocation loc) {
        return false;
    }

    @Override
    public double distanceFrom(WorldLocation loc, double direction) {
        Simulation sim = getSim();
    //    WorldLocation pixLoc = new WorldLocation (sim.world2PixelX(loc.getX()), sim.world2PixelY(loc.getY()));

        // get intersection of us and a line from the location in the given
        // direction, if any
        WorldLocation z = getIntersection(mA, mB,
                loc,
                new WorldLocation(loc.getX() + (Math.cos(direction) * 1000.0), loc.getY() + (Math.sin(direction) * 1000.0)));

        if (z != null) {
            // distance between loc and intersection
            double dx = z.getX() - loc.getX();
            double dy = z.getY() - loc.getY();
            return Math.sqrt((dx * dx) + (dy * dy));
        }

        return Double.POSITIVE_INFINITY;
    }

    @Override
    protected void consume(Message msg) {
        // don't do much, just sit here
    }

    WorldLocation getIntersection(WorldLocation p0, WorldLocation p1, WorldLocation p2, WorldLocation p3)
    {
        double s02_x, s02_y, s10_x, s10_y, s32_x, s32_y, s_numer, t_numer, denom, t;
        s10_x = p1.getX() - p0.getX();
        s10_y = p1.getY() - p0.getY();
        s32_x = p3.getX() - p2.getX();
        s32_y = p3.getY() - p2.getY();

        denom = s10_x * s32_y - s32_x * s10_y;
        if (denom == 0)
            return null; // Collinear

        boolean denomPositive = denom > 0;

        s02_x = p0.getX() - p2.getX();
        s02_y = p0.getY() - p2.getY();
        s_numer = s10_x * s02_y - s10_y * s02_x;
        if ((s_numer < 0) == denomPositive)
            return null; // No intersection

        t_numer = s32_x * s02_y - s32_y * s02_x;
        if ((t_numer < 0) == denomPositive)
            return null; // No intersection

        if (((s_numer > denom) == denomPositive) || ((t_numer > denom) == denomPositive))
            return null; // No intersection

        t = t_numer / denom;

        WorldLocation loc = new WorldLocation(p0.getX() + (t * s10_x), p0.getY() + (t * s10_y));
        return loc;
    }
}