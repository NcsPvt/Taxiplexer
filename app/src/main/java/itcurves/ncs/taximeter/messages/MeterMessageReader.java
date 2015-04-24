package itcurves.ncs.taximeter.messages;

import itcurves.ncs.Meter_Bluetooth;
import itcurves.ncs.bluebamboo.StringUtil;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import android.os.Environment;

/**
 * Trivial class for reading a message's worth of bytes from the input stream before
 * returning. Reads the first three bytes (opcode and length). It then reads in
 * length bytes.
 * 
 * @author Muhammad Zahid
 */
public class MeterMessageReader {
	/*
	 * |--------------------------------------------------------------------------|
	 * | Contructors |
	 * |--------------------------------------------------------------------------|
	 */

	public MeterMessageReader(InputStream is) {
		super();

		myInputStream = is;
		dis = new DataInputStream(is);
		ByteBuffer.allocate(BUFFER_SIZE);

	}

	/*
	 * |--------------------------------------------------------------------------|
	 * | Public Methods (interface) |
	 * |--------------------------------------------------------------------------|
	 */

	public byte[] getMessage11() throws IOException, IndexOutOfBoundsException {
		int offset = 0;
		int length = 256;
		int msgDataSize = 0;
		while (Meter_Bluetooth.isreceive) {
			if (myInputStream.available() > 0) {
				int bytesRead = myInputStream.read(pendingBuffer, offset, length);
				/*
				 * while (bytesRead < length) {
				 * length -= bytesRead;
				 * offset += bytesRead;
				 * bytesRead = myInputStream.read(pendingBuffer, offset, length);
				 * }
				 * 
				 * offset += bytesRead;
				 */
				msgDataSize = Integer.parseInt(StringUtil.returnString(pendingBuffer[2]));
				// length = msgDataSize + 2;
				// bytesRead = myInputStream.read(pendingBuffer, offset, length);
				/*
				 * while (bytesRead < length) {
				 * length -= bytesRead;
				 * offset += bytesRead;
				 * bytesRead = myInputStream.read(pendingBuffer, offset, length);
				 * }
				 */
				WriteinLogFile(new String(pendingBuffer));
				byte[] newBuffer = new byte[(msgDataSize + 5)];
				System.arraycopy(pendingBuffer, 0, newBuffer, 0, (msgDataSize + 5));
				return newBuffer;
			}
		}
		return null;
	}

	/*
	 * |--------------------------------------------------------------------------|
	 * | Public Methods (interface) |
	 * |--------------------------------------------------------------------------|
	 */

	public byte[] getMessage() throws IOException, IndexOutOfBoundsException {
		int offset = 0;
		int length = 3;
		int msgDataSize = 0;
		while (Meter_Bluetooth.isreceive) {
			if (myInputStream.available() > 0) {
				int bytesRead = myInputStream.read(pendingBuffer, offset, length);
				while (bytesRead < length) {
					length -= bytesRead;
					offset += bytesRead;
					bytesRead = myInputStream.read(pendingBuffer, offset, length);
				}
				offset += bytesRead;
				msgDataSize = Integer.parseInt(StringUtil.returnString(pendingBuffer[2]));
				length = msgDataSize + 2;
				bytesRead = myInputStream.read(pendingBuffer, offset, length);
				while (bytesRead < length) {
					length -= bytesRead;
					offset += bytesRead;
					bytesRead = myInputStream.read(pendingBuffer, offset, length);
				}
				byte[] newBuffer = new byte[(msgDataSize + 5)];
				System.arraycopy(pendingBuffer, 0, newBuffer, 0, (msgDataSize + 5));
				return newBuffer;
			}
		}
		return null;
	}

	public static void WriteinLogFile(String pMessage) {
		SetDirectoryPath();
		String ErrorLogFile = "TaxiPlexer_appLogs.txt";

		File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + DocumentDirectoryPath, ErrorLogFile);

		StringBuilder text = new StringBuilder();

		if (file.exists()) {
			// Read text from file
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;

				while ((line = br.readLine()) != null) {
					text.append(line);
					text.append('\n');
				}
				br.close();
			} catch (IOException e) {
				// You'll need to add proper error handling here
			}
		}
		text.append('\n');
		text.append(pMessage);

		FileWriter writer;
		try {
			writer = new FileWriter(file);
			writer.append(text.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void SetDirectoryPath() {
		DocumentDirectoryPath = "/TaxiPLexerdocs/";// getResources().getString(R.string.documentDirectoryPath);
		File FileDocDirectoryPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + DocumentDirectoryPath);

		if (!(FileDocDirectoryPath.exists() && FileDocDirectoryPath.isDirectory()))
			FileDocDirectoryPath.mkdir();
	}
	/*
	 * |--------------------------------------------------------------------------|
	 * | Attributes/Fields |
	 * |--------------------------------------------------------------------------|
	 */
	static String DocumentDirectoryPath = "";
	/** Initial byte of a packet. */
	public static final byte STX = 0x02;
	/** Final byte of a packet. */
	public static final byte ETX = 0x03;

	private final static int BUFFER_SIZE = 400;

	private final byte[] pendingBuffer = new byte[BUFFER_SIZE];

	private final InputStream myInputStream;

	private DataInputStream dis = null;

	// private final MeterProtocolDecoder decoder;
}
