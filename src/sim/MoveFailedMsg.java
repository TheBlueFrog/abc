package sim;

/**
 * Created by mike on 2/18/2016.
 *
 * a move failed because of an obstacle
 */
public class MoveFailedMsg extends Message {

    public MoveFailedMsg(Sensor sensor, WorldLocation dst) {
        super(sensor, dst);
    }
}
