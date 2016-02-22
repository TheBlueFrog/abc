package sim;

/**
 * message for agents, generally from another agent
 *
 * if the recipient is null it's treated as a broadcast
 */
public class Message
{
	public Agent mSender = null;
	public Class<? extends Agent> mRecipient = null;
	public Object mMessage;
	
//	public Message (Agent sender, Agent recipient, String msg)
//	{
//		mSender = sender;
//		mRecipient = recipient;
//		mMessage = msg;
//	}
//
//	public Message(String msg)
//	{
//		mMessage = msg;
//	}

	public Message(Agent sender, Object msg)
	{
		mSender = sender;
		mRecipient = null;
		mMessage = msg;
	}
	public Message(Agent sender, Class<? extends Agent> class1, Object msg)
	{
		mSender = sender;
		mRecipient = class1;
		mMessage = msg;
	}

}
