package itcurves.ncs.vivotech;

import java.nio.ByteBuffer;

public class VivoTechCommandPacket {

	private final String headerTag = "ViVOtech2\0"; // bytes 0-9
	private VivoTechCommand vivoTechCommand;
	private final int msbDataLength = 0x0; // byte 12
	private int lsbDataLength; // byte 13
	private byte[] dataBlock; // byte 14
	private int lsbCrc;
	private int msbCrc = 0x0;

	public byte[] toCmdStringByte() {

		ByteBuffer buf = ByteBuffer.allocate(dataBlock.length + headerTag.length() + 6);
		byte[] header = headerTag.getBytes();
		buf.put(header);
		buf.put((byte) vivoTechCommand.getCommand()); // byte 10
		buf.put((byte) vivoTechCommand.getSubCommand()); // byte 11
		buf.put((byte) msbDataLength);
		buf.put((byte) lsbDataLength);
		buf.put(dataBlock); // add the data block to the buffer

		// calc the checksum
		int crc = VivoTechCRC.crc16ForVivoTechVend(buf.array());
		lsbCrc = crc & 0x00FF;
		msbCrc = crc >>> 8;

		/*
		 * For pass-through packets, the Byte 14+n and Byte 15+n CRCs are the reverse of standard Version 1 Format and
		 * Version 2 Format Command packets in that the CRC(MSB) is Byte 14 and the CRC(LSB) is Byte
		 * 15 for Pass Through command packets.
		 */
		if (this.vivoTechCommand.getProtocol().equals("PT")) {
			buf.put((byte) msbCrc);
			buf.put((byte) lsbCrc);
		} else {
			buf.put((byte) lsbCrc);
			buf.put((byte) msbCrc);
		}

		return buf.array();
	}

	public int getCommand() {
		return vivoTechCommand.getCommand();
	}

	public int getSubCommand() {
		return vivoTechCommand.getSubCommand();
	}

	public byte[] getDataBlock() {
		return dataBlock;
	}

	public void setDataBlock(byte[] dataBlock) {
		this.dataBlock = dataBlock;
		this.lsbDataLength = dataBlock.length;
	}

	public VivoTechCommand getVivoTechCommand() {
		return vivoTechCommand;
	}

	public void setVivoTechCommand(VivoTechCommand vivoTechCommand) {
		this.vivoTechCommand = vivoTechCommand;
	}

}
