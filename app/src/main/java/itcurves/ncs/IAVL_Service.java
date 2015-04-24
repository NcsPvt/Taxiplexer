package itcurves.ncs;

public interface IAVL_Service {

	public boolean loggedIn();

	public void sendMessageToServer(String msgBody, String Dest, int msgType, int ackType, int validity, int priority);

	public void addMessageListener(IMessageListener listener);

	public void removeMessageListener(IMessageListener listener);

	void setloggedIn(boolean state);

	void sendAVLData();

	public void clearMsgQueues();

	boolean updateServerAddress(String address);
}
