/*
 * Created on Mar 6, 2004
 * 
 * Copyright 2004, Stock Monkey
 *
 */
package itcurves.ncs.taximeter.messages;

/**
 * @author Ryan Ziolko
 * 
 * Thrown when an input parameter is not the expected format.
 */
public class WrongFormatException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9170275319195891820L;

	public WrongFormatException()
	{
		super();
	}

	public WrongFormatException(String s)
	{
		super(s);
	}
}
