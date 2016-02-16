package sim;

/**
 * Created by mike on 2/15/2016.
 */
public class Location {

    private int mX;
    private int mY;

    public Location (int x, int y) {
        mX = x;
        mY = y;
    }
    public Location (Location l) {
        mX = l.mX;
        mY = l.mY;
    }

    public int getY() {
        return mY;
    }

    public int getX() {
        return mX;
    }

    public void move(double direction) {

        double dx = Math.cos(direction);
        double dy = Math.sin(direction);

        mX = (int) Math.round((double) mX + dx);
        mY = (int) Math.round((double) mY + dy);
    }

}
