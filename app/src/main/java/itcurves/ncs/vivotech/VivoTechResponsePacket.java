package itcurves.ncs.vivotech;

import itcurves.ncs.taximeter.messages.ByteArray;

import java.io.UnsupportedEncodingException;

import itcurves.ncs.AVL_Service;
import itcurves.ncs.IMessageListener;

//import java.nio.CharBuffer;

public class VivoTechResponsePacket {

	private final String headerTag; // bytes 0-9
	private final int command; // byte 10
	private final int statusCode; // byte 11
	private int msbDataLength = 0x0; // byte 12
	private final int lsbDataLength; // byte 13
	private byte[] dataBlock;
	private String dataBlockString;
	private String dataBlockHexString;
	private final VivoTechResponseStatusCode statusCodeString;

	public enum VivoTechResponseStatusCode {

		OK(0x0),
		INCORRECT_HEADER(0x1),
		UNKNOWN_COMMAND(0x2),
		UNKNOWN_SUBCOMMAND(0x3),
		CRC_ERROR(0x4),
		INCORRECT_PARAM(0x5),
		PARAM_NOT_SUPPORTED(0x6),
		MAL_FORMED_DATA(0x7),
		TIMEOUT(0x8),
		FAILED(0xA),
		CMD_NOT_ALLOWED(0xB),
		SUBCMD_NOT_ALLOWED(0xC),
		BUFFER_OVERFLOW(0xD),
		USER_INTERFACE_EVENT(0xE),
		REQ_ONLINE_AUTH(0x23);

		public final int statusCode;

		VivoTechResponseStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}

		public int getStatus() {
			return this.statusCode;
		}

		public static VivoTechResponseStatusCode getFromStatus(int status) throws NullPointerException {
			for (VivoTechResponseStatusCode sc : VivoTechResponseStatusCode.values()) {
				if (sc.getStatus() == status) {
					return sc;
				}
			}
			throw new NullPointerException();
		}

	}

	public VivoTechResponsePacket(byte[] bytes) throws IllegalArgumentException {
		try {
			String data = new String(bytes);
			headerTag = data.substring(0, 10);
			command = Integer.decode(data.substring(10, 11));
			statusCode = Integer.decode(data.substring(11, 12));
			msbDataLength = Integer.decode(data.substring(12, 13));
			lsbDataLength = Integer.decode(data.substring(13, 14));
			dataBlock = data.subSequence(14, (14 + lsbDataLength)).toString().getBytes();
			dataBlockString = data.subSequence(14, (14 + lsbDataLength)).toString();
			if (lsbDataLength == 4) {
				byte[] b = {0, 0, 0, 0};
				System.arraycopy(bytes, 14, b, 0, 4);
				dataBlockHexString = ByteArray.byteArrayToHexString(b);
			}
			statusCodeString = VivoTechResponseStatusCode.getFromStatus(statusCode);
		} catch (IllegalArgumentException e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in VivoTechResponsePacket in VivoTechResponsePacket][VivoTechResponsePacket][" + e.getLocalizedMessage() + "] ");
			throw new IllegalArgumentException();
		}
	}

	public String cleanedData() {
		String result = "No data in packet";
		if (null != this) {
			if (this.getDataBlock().length > 0) {
				try {
					result = new String(this.getDataBlock(), "us-ascii").trim().replace((char) 0x0, (char) 32);
				} catch (UnsupportedEncodingException e) {
					for (IMessageListener list : AVL_Service.msg_listeners.values())
						list.exception("[exception in cleanedData in VivoTechResponsePacket][cleanedData][" + e.getLocalizedMessage() + "] UnsupportedEncodingException");
				}
			}
		}
		return result;
	}

	public int getCommand() {
		return command;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public byte[] getDataBlock() {
		return dataBlock;
	}

	public String getDataBlockString() {
		return dataBlockString;
	}

	public String getDataBlockHexString() {
		return dataBlockHexString;
	}

	public String getHeaderTag() {
		return headerTag;
	}

	public int getMsbDataLength() {
		return msbDataLength;
	}

	public int getLsbDataLength() {
		return lsbDataLength;
	}

	public VivoTechResponseStatusCode getStatusCodeString() {
		return statusCodeString;
	}

}
