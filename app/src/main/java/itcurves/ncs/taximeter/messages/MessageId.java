package itcurves.ncs.taximeter.messages;

/**
 * Represents the Id of a Meter Message.
 * 
 * @author Ryan Ziolko
 */

public class MessageId {
	private final String name;
	private final int value;
	private final int length;

	private MessageId(String name, int value) {
		this.name = name;
		this.value = value;
		this.length = 1;
	}

	@Override
	public final boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public final int hashCode() {
		return super.hashCode();
	}

	@Override
	public final String toString() {
		return name;
	}

	public final int getValue() {
		return value;
	}

	public final int getLength() {
		return length;
	}

	public static MessageId getMessageId(byte value) throws InvalidMessageTypeException {
		switch (value) {
			case METER_ON_OFF_STATE_CHANGE_VALUE :
				return METER_ON_OFF_STATE_CHANGE;

			case CREDIT_CARD_DATA_VALUE :
				return SEND_CREDIT_CARD_DATA;

			case PRINT_BLOCK_VALUE :
				return PRINT_BLOCK;

			case REQUEST_METER_TRIP_DATA_VALUE :
				return REQUEST_METER_TRIP_DATA;

			case REPORT_METER_TRIP_DATA_VALUE :
				return REPORT_METER_TRIP_DATA;

			case ACKNOWLEDGE_COMMAND_VALUE :
				return ACKNOWLEDGE_COMMAND;

			case METER_BUSY_NOT_BUSY_VALUE :
				return METER_BUSY_NOT_BUSY;

			case VERIFONE_CMD1_ACK :
				return VERIFONE_CMD1_ACK_DATA;

			case VERIFONE_CASH :
				return VERIFONE_CASH_DATA;

			case VERIFONE_CREDIT_CARD :
				return VERIFONE_CREDIT_CARD_DATA;

			case VERIFONE_CMD8_NACK :
				return VERIFONE_CMD8_NACK_DATA;

			case VERIFONE_CMD2_ACK :
				return VERIFONE_CMD2_ACK_DATA;

			default :
				throw new InvalidMessageTypeException("Message Id [" + value + "] is invalid.");
		}
	}

	public static final int START_OF_TRANSMISSION = 0x02;
	public static final int END_OF_TRANSMISSION = 0x03;

	// Message ID values defined in the SM protocol
	public static final byte VERIFONE_CMD1 = 0x31;
	public static final byte VERIFONE_PING = 0x32;
	public static final byte VERIFONE_CASH = 0x33;
	public static final byte VERIFONE_CREDIT_CARD = 0x34;
	public static final byte VERIFONE_GPS = 0x35;
	public static final byte VERIFONE_PAYMENT_ACK = 0x36;
	public static final byte VERIFONE_CMD1_ACK = 0x37;
	public static final byte VERIFONE_CMD8_NACK = 0x38;
	public static final byte VERIFONE_CMD2_ACK = 0x39;
	public static final byte VERIFONE_CMD10 = 0x0A;
	public static final byte VERIFONE_LogOff = 0x0B;

	public static final byte METER_ON_OFF_STATE_CHANGE_VALUE = 0x41;
	public static final byte METER_FAILURE_STATE_CHANGE_VALUE = 0x42;
	public static final byte CREDIT_CARD_DATA_VALUE = 0x43;
	public static final byte REQUEST_METER_TRIP_DATA_VALUE = 0x44;
	public static final byte PRINT_BLOCK_VALUE = 0x45;
	public static final byte REPORT_PRINTER_STATUS_VALUE = 0x46;
	public static final byte ENABLE_DISABLE_METER_VALUE = 0x47;
	public static final byte[] UNLOCK_METER = {MessageId.START_OF_TRANSMISSION, MessageId.ENABLE_DISABLE_METER_VALUE, 0x01, 0x01, 0x45, MessageId.END_OF_TRANSMISSION};
	public static final byte[] LOCK_METER = {MessageId.START_OF_TRANSMISSION, MessageId.ENABLE_DISABLE_METER_VALUE, 0x01, 0x00, 0x44, MessageId.END_OF_TRANSMISSION};
	public static final byte REQUEST_PRINTER_STATUS_VALUE = 0x48;
	public static final byte REQUEST_METER_STATUS_VALUE = 0x49;
	public static final byte REPORT_METER_STATUS_VALUE = 0x4A;
	public static final byte REPORT_METER_TRIP_DATA_VALUE = 0x4B;
	public static final byte METER_BUSY_NOT_BUSY_VALUE = 0x4C;
	public static final byte PRINT_CREDIT_CARD_RECEIPT_VALUE = 0x4D;
	public static final byte REQUEST_METER_STATS_VALUE = 0x4E;
	public static final byte REPORT_METER_STATS_VALUE = 0x4F;
	public static final byte CLEAR_METER_STATS_VALUE = 0x50;
	public static final byte REPORT_CREDIT_CARD_AUTH_NUMBER_VALUE = 0x51;

	public static final byte UNSUPPORTED_COMMAND_VALUE = 0x59;
	public static final byte ACKNOWLEDGE_COMMAND_VALUE = 0x5A;

	public static final byte[] ACKNOWLEDGEMENT = {START_OF_TRANSMISSION, ACKNOWLEDGE_COMMAND_VALUE, 0x00, 0x58, END_OF_TRANSMISSION};
	public static final byte[] UNSUPPORTED_COMMAND = {START_OF_TRANSMISSION, UNSUPPORTED_COMMAND_VALUE, 0x00, 0x5B, END_OF_TRANSMISSION};
	public static final byte[] FLUSH_DATA_FROM_BUFFER = {START_OF_TRANSMISSION, ACKNOWLEDGE_COMMAND_VALUE, 0x00, 0x71, END_OF_TRANSMISSION};

	public static final byte REQUEST_CURRENT_METER_RATE_VALUE = 0x61;
	public static final byte REPORT_CURRENT_METER_RATE_VALUE = 0x62;

	// Pulsar
	public static final byte SET_METER_CONFIGURATION_VALUE = 0x67;
	public static final byte[] SET_METER_CONFIGURATION = {START_OF_TRANSMISSION, SET_METER_CONFIGURATION_VALUE, 0x02, 0x01, 0x00, 0x66, END_OF_TRANSMISSION};
	public static final byte REPORT_CURRENT_RUNNING_FARE_VALUE = 0x68;
	public static final byte SET_NEGOTIATED_FARE_VALUE = 0x69;
	public static final byte SET_EXTRAS_AMOUNT_VALUE = 0x70;
	public static final byte LOAD_PRINT_DATA_TO_BUFFER_VALUE = 0x6D;
	public static final byte PRINT_DATA_IN_BUFFER_VALUE = 0x6E;
	public static final byte FLUSH_PRINT_DATA_FROM_BUFFER_VALUE = 0x71;

	// Centrodyne
	public static final byte REQUEST_REPORT_METER_DAILY_STATISTICS_VALUE = 0x52;
	public static final byte CLEAR_METER_DAILY_STATISTICS_VALUE = 0x53;
	public static final byte REQUEST_REPORT_METER_CONFIGURATION_VALUE = 0x63;
	public static final byte PRINT_LARGE_BLOCK_VALUE = 0x64;

	// Message IDs defined in the meter protocol
	public static final MessageId METER_ON_OFF_STATE_CHANGE = new MessageId("Meter On/Off State Change", METER_ON_OFF_STATE_CHANGE_VALUE);
	public static final MessageId SEND_CREDIT_CARD_DATA = new MessageId("Send Credit Card Data", CREDIT_CARD_DATA_VALUE);
	public static final MessageId PRINT_BLOCK = new MessageId("Print Block", PRINT_BLOCK_VALUE);
	public static final MessageId PRINT_LARGE_BLOCK = new MessageId("Print Block", PRINT_LARGE_BLOCK_VALUE);
	public static final MessageId REQUEST_METER_TRIP_DATA = new MessageId("Request Meter Trip Data", REQUEST_METER_TRIP_DATA_VALUE);
	public static final MessageId REPORT_METER_TRIP_DATA = new MessageId("Report Meter Trip Data", REPORT_METER_TRIP_DATA_VALUE);
	public static final MessageId ACKNOWLEDGE_COMMAND = new MessageId("Acknowledge Command", ACKNOWLEDGE_COMMAND_VALUE);
	public static final MessageId METER_BUSY_NOT_BUSY = new MessageId("Meter Busy Not-Busy Data", METER_BUSY_NOT_BUSY_VALUE);
	public static final MessageId VERIFONE_CMD1_DATA = new MessageId("VeriFone CMD1", VERIFONE_CMD1);
	public static final MessageId VERIFONE_PING_DATA = new MessageId("VeriFone PING", VERIFONE_PING);
	public static final MessageId VERIFONE_PAYMENT_ACK_DATA = new MessageId("VeriFone ACK", VERIFONE_PAYMENT_ACK);
	public static final MessageId VERIFONE_CASH_DATA = new MessageId("VeriFone CASH", VERIFONE_CASH);
	public static final MessageId VERIFONE_GPS_DATA = new MessageId("VeriFone GPS", VERIFONE_GPS);
	public static final MessageId VERIFONE_CREDIT_CARD_DATA = new MessageId("VeriFone CMD1", VERIFONE_CREDIT_CARD);
	public static final MessageId VERIFONE_CMD1_ACK_DATA = new MessageId("VeriFone CMD1", VERIFONE_CMD1_ACK);
	public static final MessageId VERIFONE_CMD8_NACK_DATA = new MessageId("VeriFone CMD8", VERIFONE_CMD8_NACK);
	public static final MessageId VERIFONE_CMD2_ACK_DATA = new MessageId("VeriFone CMD9", VERIFONE_CMD2_ACK);
	public static final MessageId VERIFONE_CMD10_DATA = new MessageId("VeriFone CMD10", VERIFONE_CMD10);
	public static final MessageId VERIFONE_LogOff_DATA = new MessageId("VeriFone LogOff", VERIFONE_LogOff);
}
