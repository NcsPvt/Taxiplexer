package itcurves.ncs.vivotech;

public enum VivoTechCommand {
	PING(0x18, 0x01, "V2"),
	CLEAR_LCD(0xF0, 0xF9, "V2"),
	SET_POLL_MODE(0x01, 0x01, "V2"),
	ACTIVATE_TRANSACTION(0x02, 0x01, "V2"),
	BEEP(0x0B, 0x02, "PT"),
	SET_LINE_1(0xF0, 0xFC, "V2"),
	SET_LINE_2(0xF0, 0xFD, "V2"),
	DISABLE_SWIPE_AND_DONE_BUTTON(0xF0, 0xF4, "V2"),
	DISABLE_BLUE_LED(0xF0, 0xF6, "V2"),
	ENABLE_BLUE_LED(0xF0, 0xF7, "V2"),
	DISABLE_YELLOW_LED(0xF0, 0xFA, "V2"),
	ENABLE_YELLOW_LED(0xF0, 0xFB, "V2"),
	PASS_THROUGH_MODE(0x2C, 0x01, "PT"),
	CLEAR_DISPLAY_8800(0x83, 0x0D, "V2"),
	SET_COLORS_8800(0x83, 0x0A, "V2"),
	DISPLAY_TEXT_8800(0x83, 0x03, "V2"),
	DISPLAY_AMT_8800(0x83, 0x04, "V2"),
	DISPLAY_BUTTON_8800(0x83, 0x05, "V2"),
	START_CUSTOM_DISPLAY_8800(0x83, 0x08, "V2"),
	STOP_CUSTOM_DISPLAY_8800(0x83, 0x09, "V2"),
	CLEAR_EVENT_QUEUE_8800(0x83, 0x0C, "V2"),
	GET_INPUT_EVENT_8800(0x83, 0x06, "V2"),
	CREATE_INPUT_FIELD_8800(0x83, 0x1C, "V2"),
	GET_INPUT_FIELD_8800(0x83, 0x1D, "V2"),
	CLEAR_INPUT_FIELD_8800(0x83, 0x1E, "V2"),
	CANCEL_TRANS_OR_INPUT(0x05, 0x01, "V2"),
	DISPLAY_SLIDE_8800(0x83, 0x0E, "V2"),
	BEGIN_SLIDESHOW_8800(0x83, 0x20, "V2"),
	CANCEL_SLIDESHOW_8800(0x83, 0x21, "V2"),
	DELETE_FILE_8800(0x83, 0x1F, "V2"),
	LIST_DIR_8800(0x83, 0x22, "V2"),
	GET_DRIVE_FREE_SPACE_8800(0x83, 0x23, "V2"),
	TRANSFER_FILE_8800(0x83, 0x24, "V2"),
	CREATE_DIR_8800(0x83, 0x25, "V2");

	private final int command;
	private final int subCommand;
	private final String protocol;

	VivoTechCommand(int command, int subCommand, String protocol) {
		this.command = command;
		this.subCommand = subCommand;
		this.protocol = protocol;
	}

	public int getCommand() {
		return command;
	}

	public int getSubCommand() {
		return subCommand;
	}

	public String getProtocol() {
		return protocol;
	}

}
