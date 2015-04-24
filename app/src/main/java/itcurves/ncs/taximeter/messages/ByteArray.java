/*
 * Created on Mar 6, 2004
 * 
 * Copyright 2004, Stock Monkey
 *
 */
package itcurves.ncs.taximeter.messages;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

/**
 * Create an manipulates an array of bytes. It can also be used for
 * converting to/from strings.
 * 
 * @author Ryan Ziolko
 */
public class ByteArray {
	/**
	 * Each byte in the byte array becomes two characters in the returned string.
	 * 
	 * @param b a byte array to be turned into a string
	 * @return A string containing an ascii representation of the input byte array.
	 */
	public static String byteArrayToHexString(byte[] b) {
		return byteArrayToHexStringWithDelimiter(b, "");
	}

	/**
	 * Each byte in the byte array becomes two characters in the returned string. Each two character byte representation is
	 * followed by the delimiter character(s), except for the last byte.
	 * 
	 * @param b a byte array to be turned into a string
	 * @param delim character(s) to be placed in between the string representations of each byte
	 * @return A string containing an ascii representation of the input byte array.
	 * @see Utilities#byteArrayToHexString
	 */
	public static String byteArrayToHexStringWithDelimiter(byte[] b, String delim) {
		if (b == null)
			return "";
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		StringBuffer sb = new StringBuffer((2 + delim.length()) * bais.available());
		while (bais.available() > 0) {
			String s = Integer.toHexString(bais.read());
			if (s.length() == 1)
				sb.append("0");
			sb.append(s);
			if (bais.available() > 0)
				sb.append(delim);
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * This method assumes that there is no delimiter in the hex string.
	 * 
	 * @param s - a string where each character represents a hex nibble
	 * @return A byte array representation of the string parameter
	 * @exception WrongFormatException if the string has an odd number of characters
	 * @exception java.lang.NumberFormatException if the characters in the string cannot be represented as a hex number
	 */
	public static byte[] hexStringToByteArray(String s) throws WrongFormatException {
		if ((s.length() % 2) != 0)
			throw new WrongFormatException(s);
		byte[] b = new byte[s.length() / 2];
		String shi, slo;
		byte hi, lo;
		for (int i = 0; i < b.length; i++) {
			shi = s.substring(2 * i, 2 * i + 1);
			slo = s.substring(2 * i + 1, 2 * i + 2);
			hi = Byte.parseByte(shi, 16);
			lo = Byte.parseByte(slo, 16);
			b[i] = (byte) ((hi << 4) + lo);
		}
		return b;
	}

	public static byte[] hexDigitsToByteArray(byte[] h) throws WrongFormatException {
		if ((h.length % 2) != 0)
			throw new WrongFormatException();
		byte[] b = new byte[h.length / 2];
		byte hi, lo;
		for (int i = 0; i < b.length; i++) {
			hi = h[2 * i];
			if ((hi >= 48) && (hi <= 57)) {
				hi -= 48;
			} else if ((hi >= 65) && (hi <= 70)) {
				hi -= 55;
			} else {
				throw new WrongFormatException();
			}
			lo = h[2 * i + 1];
			if ((lo >= 48) && (lo <= 57)) {
				lo -= 48;
			} else if ((lo >= 65) && (lo <= 70)) {
				lo -= 55;
			} else {
				throw new WrongFormatException();
			}
			b[i] = (byte) (hi * 16 + lo);
		}
		return b;
	}

	/**
	 * Extracts an integer from a byte array
	 * 
	 * @param the byte array
	 * @param the index to extract from
	 * @param the number of bytes to extract
	 */
	public static int extractInt(byte[] byteArray, int start, int len) {
		if ((len < 1) || (len > 4))
			throw new IllegalArgumentException("Length must be a number between 1 and 4");
		if (byteArray.length < start + len - 1) {
			throw new ArrayIndexOutOfBoundsException("Array length: " + byteArray.length + " is less that start + length - 1 (" + (start + len - 1) + ")");
		}
		int value = 0;
		try {
			if (byteArray[start] < 0) {
				int min = 256;
				return ((byteArray[start] + min) & 0xFF);
			}
		} catch (Exception ex) {

		}
		for (int i = 0; i < len; i++) {
			value = (value << 8) + ((byteArray[start + i]) & 0xFF);
		}
		return value;
	}

	/**
	 * Extracts a long integer from a byte array
	 * 
	 * @param the byte array
	 * @param the index to extract from
	 * @param the number of bytes to extract
	 */
	public static long extractLong(byte[] byteArray, int start, int len) {
		if ((len < 1) || (len > 8))
			throw new IllegalArgumentException("Length must be a number between 1 and 8");
		if (byteArray.length < start + len - 1) {
			throw new ArrayIndexOutOfBoundsException("Array length: " + byteArray.length + " is less that start + length - 1 (" + (start + len - 1) + ")");
		}
		long value = 0;
		for (int i = 0; i < len; i++) {
			value = (value << 8) + ((byteArray[start + i]) & 0xFF);
		}
		return value;
	}

	/**
	 * extracts a subset of a byte array
	 * 
	 * @param the byte array
	 * @param the start of the subset to extract
	 * @param the number of bytes to extract
	 * @return the subset byte array
	 */
	public static byte[] extractByteArray(byte[] byteArray, int start, int length) {
		if (byteArray.length < start + length - 1) {
			throw new ArrayIndexOutOfBoundsException("Array length: " + byteArray.length + " is less that start + length - 1 (" + (start + length - 1) + ")");
		}
		byte[] value = new byte[length];
		for (int i = 0; i < length; i++) {
			value[i] = byteArray[start + i];
		}
		return value;
	}

	public static void insertInt(byte[] b, int start, int length, int value) {
		// if the passed length is 0, no effect to b, just return
		if (length == 0)
			return;
		if ((length < 1) || (length > 4))
			throw new IllegalArgumentException("Length must be a number between 1 and 4");
		if ((start < 0) || (start > b.length - 1)) {
			throw new IllegalArgumentException("Size must be a number between 0 and the size of the array (" + b.length + ")");
		}
		if (start + length > b.length) {
			throw new ArrayIndexOutOfBoundsException("Can't insert outside of the array: " + start + " + " + length + " > " + b.length);
		}
		for (int i = length - 1; i >= 0; i--) {
			b[start + i] = (byte) (value);
			value = value >> 8;
		}
	}

	public static void insertLong(byte[] b, int start, int length, long value) {
		// if the passed length is 0, no effect to b, just return
		if (length == 0)
			return;
		if ((length < 1) || (length > 8))
			throw new IllegalArgumentException("Length must be a number between 1 and 4");
		if ((start < 0) || (start > b.length - 1)) {
			throw new IllegalArgumentException("Size must be a number between 0 and the size of the array (" + b.length + ")");
		}
		if (start + length > b.length) {
			throw new ArrayIndexOutOfBoundsException("Can't insert outside of the array: " + start + " + " + length + " > " + b.length);
		}
		for (int i = length - 1; i >= 0; i--) {
			b[start + i] = (byte) (value);
			value = value >> 8;
		}
	}

	/**
	 * This method compares two byte arrays. Return true if the arrays contain the same data, false if they don't.
	 * 
	 * @param byte[] the first of the two byte arrays being compared
	 * @param byte[] the second of the two byte arrays being compared
	 * @return true if the two arrays contain the same data
	 */
	public static boolean compareByteArrays(byte[] a, byte[] b) {
		return Arrays.equals(a, b);
	}

	public static byte[] shortToByte(short myInt) {
		byte[] bytes = new byte[4];
		// 0xff A byte of all ones
		bytes[0] = (byte) (0xff & myInt);
		bytes[1] = (byte) (((0xff << 8) & myInt) >> 8);
		bytes[2] = (byte) (((0xff << 16) & myInt) >> 16);
		bytes[3] = (byte) (((0xff << 24) & myInt) >> 24);
		return bytes;
	}

	public static short byteToShort(byte[] bytes) {
		short myInt = bytes[3];
		myInt = (short) ((myInt << 8) | bytes[2]);
		myInt = (short) ((myInt << 8) | bytes[1]);
		myInt = (short) ((myInt << 8) | bytes[0]);
		return myInt;
	}

	public static long byteToInt(byte[] by) {
		long value = 0;
		for (int i = 0; i < by.length; i++) {
			value += (by[i] & 0xff) << (8 * i);
		}
		return value;
	}
}
