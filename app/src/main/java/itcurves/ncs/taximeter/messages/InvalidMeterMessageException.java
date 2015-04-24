package itcurves.ncs.taximeter.messages;

/**
 * Thrown when parsing a meter message that does not parse properly.
 * 
 * @author Ryan Ziolko
 */
public class InvalidMeterMessageException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2475055553233944223L;

	public InvalidMeterMessageException()
	{
		super();
	}

	public InvalidMeterMessageException(String s)
	{
		super(s);
	}
}