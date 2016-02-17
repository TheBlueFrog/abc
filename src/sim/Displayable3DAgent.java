package sim;

import java.awt.*;

/**
 * Created by mike on 2/16/16.
 *
 * displayable objects in reality
 */
public abstract class Displayable3DAgent extends Agent {

    public Displayable3DAgent(Simulation sim) {
        super(sim.getFramework());
    }

    abstract public void paint(Graphics2D g2, double real2PixelX, double real2PixelY);

    public abstract boolean isInside(Simulation sim, Location loc);
}
