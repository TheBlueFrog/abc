package sim;

import java.awt.*;

/**
 * Created by mike on 2/16/16.
 */
public class Static3DRect extends Static3DAgent {

    private final int[] x = new int[4];
    private final int[] y= new int[4];

    public Static3DRect(Simulation sim,
                        double ulx, double uly,
                        double urx, double ury,
                        double llx, double lly,
                        double lrx, double lry) {
        super(sim);

        x[0] = (int) ulx;
        x[1] = (int) urx;
        x[2] = (int) lrx;
        x[3] = (int) llx;
        y[0] = (int) uly;
        y[1] = (int) ury;
        y[2] = (int) lry;
        y[3] = (int) lly;
    }

    @Override
    public void paint(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fillPolygon(x, y, 4);
    }
}