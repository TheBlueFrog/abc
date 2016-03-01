package sim;

/**
 * Created by mike on 2/14/2016.
 */
public class Main {

    public static Drawing mDrawing;
    private static Controls mControls;
    public static Simulation simulation;

    static private final double worldMax = 1000.0;
    static private final double world2Pixel = 0.5;

    public static void main(String[] args) {

        simulation =  new Simulation(worldMax, worldMax, world2Pixel, world2Pixel);

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // create the controls and drawing windows
                mControls = new Controls();

                // number of rows and columns in the Simulation grid
                mDrawing = new Drawing(
                        (int) Math.round(worldMax * world2Pixel),
                        (int) Math.round(worldMax * world2Pixel),
                        1.0 / world2Pixel);
            }
        });
    }

    static public void log(String tag, String s) {
        System.out.println(String.format("%20s: %s", tag, s));
    }

}
