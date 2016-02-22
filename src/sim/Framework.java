package sim;

import java.util.ArrayList;
import java.util.List;


public class Framework
{
	private List<Agent> agents = new ArrayList<Agent>();

	public void send(Message m)
	{
		for (Agent a : getRecipients(m.mRecipient))
			a.incoming(m);
	}

	public void register(Agent agent)
	{
		agents.add(agent);
	}

	private List<Agent> getRecipients(Class<? extends Agent> class1)
	{
		List<Agent> v = new ArrayList<Agent>();
		
		for (Agent a : agents)
		{
			if ((class1 == null) || (class1.isAssignableFrom(a.getClass())))
				v.add(a);
		}
		
		return v;
	}

	public void log(String tag, String s) {
        Main.log(tag, s);
	}
}
