package itcurves.ncs;

public interface IAVLServiceStatusListener {

	public void networkServiceStarted(IAVL_Service service);
	public void networkServiceStoppped(IAVL_Service service);
}
