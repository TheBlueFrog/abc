package sim;

/**
 * Created by mike on 2/14/2016.
 */
public class Cell {
    static public final int SensorFull = 1;
    static public final int SensorEmpty = 2;
    
    
    private int state;
    
    public int getState() {
        return state;
    }

    public Cell () {
        this.state = SensorEmpty;
    }
    public Cell (int state) {
        this.state = state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
