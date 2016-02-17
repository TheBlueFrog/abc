package sim;

/**
 * Created by mike on 2/16/2016.
 */
public abstract class TickableAgent extends Agent {

    public TickableAgent(Simulation sim, double activationIntensity) {
        super(sim.getFramework());
        mActivationIntensity = activationIntensity;
    }

    abstract public void tick(Simulation sim, Sensor sensor);

    public double mActivationIntensity = 0.0;
}
