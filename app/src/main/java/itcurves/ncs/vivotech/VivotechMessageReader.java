package itcurves.ncs.vivotech;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import itcurves.ncs.AVL_Service;
import itcurves.ncs.IMessageListener;

/**
 * Trivial class for reading a message's worth of bytes from the input stream before
 * returning. Reads the first 14 bytes. It then reads in length bytes.
 * 
 * @author Muhammad Zahid
 */
public class VivotechMessageReader {
	private String rcvdString;
	private byte[] responsePacket;

	/*
	 * |--------------------------------------------------------------------------|
	 * | Contructors |
	 * |--------------------------------------------------------------------------|
	 */

	public VivotechMessageReader(InputStream is) {
		super();

		myInputStream = is;

		ByteBuffer.allocate(1024);

	}

	/*
	 * |--------------------------------------------------------------------------|
	 * | Public Methods (interface) |
	 * |--------------------------------------------------------------------------|
	 */

	/*
	 * 00000000
	 * 0000010A
	 * 047279616E120A6D7970617373776F72641A2438463946413444312D463233412D353043332D424435362D3931333834433836
	 */
	public byte[] getNextMessage() throws IOException {
		byte[] infoBytes = new byte[14];
		try {
			myInputStream.read(infoBytes, 0, 14);
			rcvdString = new String(infoBytes);
			byte[] dataBytes = new byte[Integer.valueOf(rcvdString.charAt(13)) + 2];
			myInputStream.read(dataBytes, 0, Integer.valueOf(rcvdString.charAt(13)) + 2);

			responsePacket = new byte[infoBytes.length + dataBytes.length];
			System.arraycopy(infoBytes, 0, responsePacket, 0, infoBytes.length);
			System.arraycopy(dataBytes, 0, responsePacket, infoBytes.length, dataBytes.length);

			return responsePacket;
		} catch (IndexOutOfBoundsException ex) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in getNextMessage in VivotechMessageReader][getNextMessage][" + ex.getLocalizedMessage() + "] ");
			return null;
		}
	}// getNextMessage()

	/*
	 * |--------------------------------------------------------------------------|
	 * | Attributes/Fields |
	 * |--------------------------------------------------------------------------|
	 */

	private final InputStream myInputStream;

}
