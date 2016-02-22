package sim;

/**
 * this agent owns the direction the sensor is moving
 */
public class SensorHeading extends TickingAgent
{
	private static final String TAG = SensorHeading.class.getSimpleName();

	private static double mHeading = 0.0; // east

	public SensorHeading(Simulation sim)
	{
		super(sim, 0);
	}

    @Override
    public void tick(Simulation sim, Sensor sensor) {

    }

    @Override
	protected void consume(Message msg)
	{
        Sensor sensor = (Sensor) msg.mMessage;

		mFramework.log(TAG, String.format("Heading now %2.5f", mHeading));
	}

    public double getHeading() {
        return mHeading;
    }
    public void setHeading(double h) {
        this.mHeading = h;
    }
}
