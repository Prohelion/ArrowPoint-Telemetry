package au.com.teamarrow.canbus.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jason on 8/17/2015.
 */
public class ArrowMessage {

    private String sender = null;
    private String message = null;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    private Date timeStamp = null;

    public ArrowMessage() {
    	this.timeStamp = new Date();
    }
        
    public ArrowMessage(String message) {
    	this.message = message;
    	this.timeStamp = new Date();
    }
    
    public ArrowMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
        this.timeStamp = new Date();
    }

    @Override
    public String toString() {
        return  simpleDateFormat.format(timeStamp) + " - " + message;
    }

    public String getMessageContent(){
        return message;
    }

    public int getSecondsSince(){
        Date now = new Date();
        long difference = now.getTime() - timeStamp.getTime();
        return (int)difference/1000;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }





}

