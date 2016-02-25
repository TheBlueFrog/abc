package sim;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by mike on 2/16/16.
 */
public class Static3DWall extends Displayable3DAgent {

    private final Location mA;
    private final Location mB;
    private Line2D mLine;

    public Static3DWall(Simulation sim, double x, double y, double x1, double y1) {
        super(sim);

        mA = new Location(x, y);
        mB = new Location(x1, y1);
    }
    public Static3DWall(Simulation sim, Location a, Location b) {
        super(sim);

        mA = new Location(a);
        mB = new Location(b);
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
    public boolean isInside(Simulation sim, Location loc) {
        return false;
    }

    @Override
    public double distanceFrom(Location loc, double direction) {
        Simulation sim = getSim();
    //    Location pixLoc = new Location (sim.real2PixelX(loc.getX()), sim.real2PixelY(loc.getY()));

        // get intersection of us and a line from the location in the given
        // direction, if any
        Location z = getIntersection(mA, mB,
                loc,
                new Location(loc.getX() + (Math.cos(direction) * 1000.0), loc.getY() + (Math.sin(direction) * 1000.0)));

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

    Location getIntersection(Location p0, Location p1, Location p2, Location p3)
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

        Location loc = new Location(p0.getX() + (t * s10_x), p0.getY() + (t * s10_y));
        return loc;
    }
}