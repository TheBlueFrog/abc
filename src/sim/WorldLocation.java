package sim;

/**
 * Created by mike on 2/15/2016.
 */
public class WorldLocation {

    private double mX;
    private double mY;

    public WorldLocation(double x, double y) {
        mX = x;
        mY = y;
    }
    public WorldLocation(WorldLocation l) {
        mX = l.mX;
        mY = l.mY;
    }

    public double getY() {
        return mY;
    }

    public double getX() {
        return mX;
    }

    public void move(double direction, double velocity) {
        mX += Math.cos(direction) * velocity;
        mY += Math.sin(direction) * velocity;
    }

}
