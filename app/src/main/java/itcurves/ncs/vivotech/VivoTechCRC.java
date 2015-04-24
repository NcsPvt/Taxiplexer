package itcurves.ncs.vivotech;

public class VivoTechCRC {

	private static final int poly = 0x1021;/* x16 + x12 + x5 + 1 generator polynomial */
	/* 0x8408 used in European X.25 */

	private static final int[] crcTable = new int[256];

	static {
		// initialise scrambler table
		for (int i = 0; i < 256; i++) {
			int fcs = 0;
			int d = i << 8;
			for (int k = 0; k < 8; k++) {
				if (((fcs ^ d) & 0x8000) != 0) {
					fcs = (fcs << 1) ^ poly;
				} else {
					fcs = (fcs << 1);
				}
				d <<= 1;
				fcs &= 0xffff;
			}
			crcTable[i] = fcs;
		}
	}

	/**
	 * Calc 16-bit CRC with CCITT method.
	 * 
	 * @param ba byte array to compute CRC on
	 * @return 16-bit CRC, unsigned
	 */

	public static int crc16ForVivoTechVend(byte[] ba) {
		// subtracts two bytes so checksum bytes from buffer not included
		return crc16(ba, ba.length - 2);
	}

	public static int crc16(byte[] ba) {
		return crc16(ba, ba.length);
	}

	public static int crc16(byte[] ba, int len) {
		// loop, calculating CRC for each byte of the string
		int crc = 0xffff;
		for (int i = 0; i < len; i++) {
			// xor the next data byte with the high byte of what we have so far to
			// look up the scrambler.
			// xor that with the low byte of what we have so far.
			// Mask back to 16 bits.
			crc = (crcTable[(ba[i] ^ (crc >>> 8)) & 0xff] ^ (crc << 8)) & 0xffff;
		}
		return crc;
	}
}
