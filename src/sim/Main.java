package sim;

/**
 * Created by mike on 2/14/2016.
 */
public class Main {

    public static Drawing mDrawing;
    private static Controls mControls;
    public static Simulation simulation;

    public static void main(String[] args) {

        simulation =  new Simulation(200.0, 200.0, 3.0, 3.0);

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // create the controls and drawing windows
                mControls = new Controls();

                // number of rows and columns in the Simulation grid
                mDrawing = new Drawing(200, 200);
            }
        });
    }

    static public void log(String tag, String s) {
        System.out.println(String.format("%20s: %s", tag, s));
    }

}
