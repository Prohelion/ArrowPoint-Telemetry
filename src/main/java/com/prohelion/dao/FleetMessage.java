package com.prohelion.dao;

public class FleetMessage {

	public final static int RECEIVER_TEAM = 0;
	public final static int RECEIVER_ARROW1 = 1;
	public final static int RECEIVER_LEAD = 2;
	public final static int RECEIVER_CHASE = 3;
	
	public final static int MESSAGE_TYPED = 0;
	public final static int MESSAGE_COMMS_CHECK = 1;
	public final static int MESSAGE_LOST_COMMS = 2;
	public final static int MESSAGE_TARGET = 3;
	public final static int MESSAGE_BOXBOXBOX = 4;	
	
	private String message = null;	
	private Integer messageOptions = -1;	
	private Integer messageReceiver = -1;	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getMessageOptions() {
		return messageOptions;
	}
	public void setMessageOptions(Integer messageOptions) {
		this.messageOptions = messageOptions;
	}
	public Integer getMessageReceiver() {
		return messageReceiver;
	}
	public void setMessageReceiver(Integer messageReceiver) {
		this.messageReceiver = messageReceiver;
	}
	
	
	
}
