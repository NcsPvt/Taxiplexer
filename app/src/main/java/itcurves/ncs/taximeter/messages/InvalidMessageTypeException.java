package itcurves.ncs.taximeter.messages;

/**
 * Thrown when attempting to process a Message ID that is
 * not valid in the meter protocol.
 * 
 * @author Ryan Ziolko
 */
public class InvalidMessageTypeException extends InvalidMeterMessageException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5909549742974042207L;

	public InvalidMessageTypeException()
	{
		super();
	}

	public InvalidMessageTypeException(String s)
	{
		super(s);
	}
}

