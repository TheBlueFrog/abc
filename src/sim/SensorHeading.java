package sim;

/**
 * this agent owns the direction the sensor is moving
 */
public class SensorHeading extends Agent
{
	private static final String TAG = SensorHeading.class.getSimpleName();

	private static double mHeading = 0.0; // east

	public SensorHeading(Framework f)
	{
		super(f);
	}

	@Override
	protected void consume(Message msg)
	{
		double d = (Double) msg.mMessage;

		mHeading += d;
		
		mFramework.log(TAG, String.format("Heading now %2.5f, %s changed it by %2.5f",
                mHeading,
				msg.mSender.getTag(),
				d));
	}

    public double getHeading() {
        return mHeading;
    }
    public void setHeading(double h) {
        this.mHeading = h;
    }
}
