package itcurves.ncs;

public class MsgPriority {

	public static final int Lowest = 0; // ?
	public static final int BelowNormal = 1; // AVL & Text Messages
	public static final int Normal = 2; // Bid and trip response messages
	public static final int AboveNormal = 3; // login/handshake messages
	public static final int Highest = 4; // Emergency Mesages/payment messages

}