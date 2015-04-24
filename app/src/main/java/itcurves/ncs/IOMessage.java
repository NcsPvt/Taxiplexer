package itcurves.ncs;

import java.net.DatagramPacket;

public class IOMessage {

	private String[] packetArray;
	private String header;
	private String body;
	private int mMsgType;
	private String mMsgTag; // mmddyyyyhhmmssSSS
	private String mMsgSourceID;
	private String mMsgDestID;
	private int mMsgCounter;
	private String mAckType; // MessageStatus(NEW/ACC/REJ/ACK/NAK)
	private String mMsgValidity;
	private String mMsgPriority;
	private String mIsMsgProceeding; // 0/1
	private String mMsgBodyLength;
	DatagramPacket _outPacket;
	private long msgSendTime;

	public IOMessage(DatagramPacket outPacket, String msgTag, long msgID, int msgType, String msgBody) {
		this._outPacket = outPacket;
		this.mMsgTag = msgTag;
		this.msgSendTime = msgID;
		this.mMsgType = msgType;
		this.mMsgCounter = 1;
		this.body = msgBody;
	}

	public IOMessage(String Packetdata) {

		String temp = Packetdata;
		this.packetArray = temp.split(Character.toString(Constants.BODYSEPARATOR));
		this.header = packetArray[0];
		String[] headerArray = header.split("\\" + Character.toString(Constants.COLSEPARATOR));
		this.body = packetArray[1];

		this.mMsgType = Integer.valueOf(headerArray[0]);
		this.mMsgTag = headerArray[1];
		this.mMsgSourceID = headerArray[2];
		this.mMsgDestID = headerArray[3];
		this.mMsgCounter = Integer.valueOf(headerArray[4]);
		this.mAckType = headerArray[5];
		this.mMsgValidity = headerArray[6];
		this.mMsgPriority = headerArray[7];
		this.mIsMsgProceeding = headerArray[8];
		this.mMsgBodyLength = headerArray[9];

	}// Constructor

	public void setType(int mMsgType) {
		this.mMsgType = mMsgType;
	}

	public String getHeader() {
		return header;
	}

	public int getType() {
		return Integer.valueOf(mMsgType);
	}

	public void setTag(String mMsgTag) {
		this.mMsgTag = mMsgTag;
	}

	public String getTag() {
		return mMsgTag;
	}

	public void setSourceID(String mMsgSourceID) {
		this.mMsgSourceID = mMsgSourceID;
	}

	public String getSourceID() {
		return mMsgSourceID;
	}

	public void setDestID(String mMsgDestID) {
		this.mMsgDestID = mMsgDestID;
	}

	public String getDestID() {
		return mMsgDestID;
	}

	public void setCounter(int mMsgCounter) {
		this.mMsgCounter = mMsgCounter;
	}

	public int getCounter() {
		return mMsgCounter;
	}

	public void setAckType(String mAckType) {
		this.mAckType = mAckType;
	}

	public String getAckType() {
		return mAckType;
	}

	public void setValidity(String mMsgValidity) {
		this.mMsgValidity = mMsgValidity;
	}

	public String getValidity() {
		return mMsgValidity;
	}

	public void setPriority(String mMsgPriority) {
		this.mMsgPriority = mMsgPriority;
	}

	public String getPriority() {
		return mMsgPriority;
	}

	public void setIsProceeding(String mIsMsgProceeding) {
		this.mIsMsgProceeding = mIsMsgProceeding;
	}

	public String isMsgProceeding() {
		return mIsMsgProceeding;
	}

	public void setBodyLength(String mMsgBodyLength) {
		this.mMsgBodyLength = mMsgBodyLength;
	}

	public String getBodyLength() {
		return mMsgBodyLength;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	public void setSendTime(long msgSendTime) {
		this.msgSendTime = msgSendTime;
	}

	public long getSendTime() {
		return msgSendTime;
	}

	public boolean equals(IOMessage rcvd, IOMessage sent) {
		return rcvd.mMsgTag == sent.mMsgTag;
	}

}
