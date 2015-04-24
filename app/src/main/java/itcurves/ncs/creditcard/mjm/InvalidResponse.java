package itcurves.ncs.creditcard.mjm;

public class InvalidResponse extends Exception {

	/**
	 * Thrown when attempting to process a XML Reponse Message that is
	 * not valid in the Credit Card protocol.
	 * 
	 * @author Muhammad Zahid
	 */
	private static final long serialVersionUID = 1L;
	public InvalidResponse() {
		super();
	}

	public InvalidResponse(String s) {
		super(s);
	}

}
