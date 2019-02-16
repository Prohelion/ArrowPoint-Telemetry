package com.prohelion.canbus.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ctuesley on 5/05/2015.
 */
public class CarData {

    private int lastSpeed = (int) 0;
    private double lastSOC = (float) 100;
    private double lastLockedSOC = (float) 0;
    private double lastArray1Volts = (double) 0;
    private double lastArray2Volts = (double) 0;
    private double lastArray3Volts = (double) 0;
    private double lastArray1Amps = (double) 0;
    private double lastArray2Amps = (double) 0;
    private double lastArray3Amps = (double) 0;
    private double lastBusPower = (double) 0.0;
    private double lastBusAmps = (double) 0.0;
    private double lastBusVolts = (double) 0.0;
    private double lastBatteryAmps = (double) 0.0;
    private double lastBatteryVolts = (double) -1.0;
    private double lastMotorTemp = (double) 0.0;
    private double lastControllerTemp = (double) 0.0;
    private int lastMinimumCellV = (int) -1;
    private int lastMaximumCellV = (int) 0;
    private int lastTwelveVBusVolts = (int) -1;
    private int lastMaxCellTemp = (int) 0;
    private int lastMotorPowerSetpoint = 0;
    private int lastMaxSOMSetpoint = 100;

    private double latitude = (double) 0;
    private double longitude = (double) 0;

    private double MinThreshMinimumCellV = (double)2.9;
    private int MaxThreshMotorTemp = 100;
    private int MaxThreshMaxCellTemp = 60;
    private int MaxThreshControllerTemp = 50;

    private boolean cruiseControl = false;
    private boolean setPointCruiseControl = false;
    private boolean speedCruiseControl = false;
    private boolean SOMCruiseControl = false;
    private int cruiseTargetSpeed = 0;
    private boolean leftBlinker = false;
    private boolean rightBlinker = false;
    private boolean hazard = false;
    private boolean idle = false;
    private boolean reverse = false;
    private boolean neutral = false;
    private boolean drive = false;
    private boolean run = false;
    private boolean regen = false;
    private boolean brakes = false;
    private boolean horn = false;
    private boolean testLayout = false; // Only used to test the layout sizes and positioning
    private int msSinceLastPacket = 0;
    private int secSinceLastPacket = 0;
    private String alerts = null;
    private ArrowMessage driverMessage = null;
    private boolean driverMode = true;
    private String sendMessage = null;
    private int MAX_LIST_SIZE = 10;
    private List<ArrowMessage> messageList = new ArrayList<ArrowMessage>();


    public double getMinThreshMinimumCellV() {
        return MinThreshMinimumCellV;
    }

    public void setMinThreshMinimumCellV(double MinThreshMinimumCellV) {
        this.MinThreshMinimumCellV = MinThreshMinimumCellV;
    }

    public int getMaxThreshMotorTemp() {
        return MaxThreshMotorTemp;
    }

    public void setMaxThreshMotorTemp(int MaxThreshMotorTemp) {
        this.MaxThreshMotorTemp = MaxThreshMotorTemp;
    }

    public int getMaxThreshMaxCellTemp() {
        return MaxThreshMaxCellTemp;
    }

    public void setMaxThreshMaxCellTemp(int MaxThreshMaxCellTemp) {
        this.MaxThreshMaxCellTemp = MaxThreshMaxCellTemp;
    }

    public int getMaxThreshControllerTemp() {
        return MaxThreshControllerTemp;
    }

    public void setMaxThreshControllerTemp(int MaxThreshControllerTemp) {
        this.MaxThreshControllerTemp = MaxThreshControllerTemp;
    }

    public int getLastMaxSOMSetpoint() {
        return lastMaxSOMSetpoint;
    }

    public void setLastMaxSOMSetpoint(int lastMaxSOMSetpoint) {
        this.lastMaxSOMSetpoint = lastMaxSOMSetpoint;
    }

    public boolean isDriverMode() {
        return driverMode;
    }

    public void setDriverMode(boolean driverMode) {
        this.driverMode = driverMode;
    }


    public String getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
    }

    public void addMessage(String sender, String message){

        if (message.contains("-ca")){ // Clear all Messages
            messageList.clear();
            driverMessage = null;
        } else if (message.contains("-cd")){ //Clear Driver Message
            driverMessage = null;
        } else if (message.contains("-d")){ //Driver Message
            message = message.replace("-d ", "");
            driverMessage = new ArrowMessage(sender,message);
            //message = message.concat(" (Driver Message)");
            messageList.add(new ArrowMessage(sender,message));
        } else {
            messageList.add(new ArrowMessage(sender,message));
        }

        if (messageList.size() > MAX_LIST_SIZE) {
            messageList.remove(0);
        }

    }

    public String getMessages(){
        String string = "";
        for (int i = 0; i < messageList.size(); i ++){
            string += messageList.get(messageList.size()-1-i).toString() + "\n";
        }
        return string;
    }

    public ArrowMessage getDriverMessage() {
        if (driverMode) {
            return driverMessage;
        }
        if (messageList.size() > 0) {
            return messageList.get(messageList.size()-1);
        }
        return null;
    }

    public void setDriverMessage(ArrowMessage driverMessage) {
        this.driverMessage = driverMessage;
    }

    public int getMsSinceLastPacket() {
        return msSinceLastPacket;
    }

    public void setMsSinceLastPacket(int msSinceLastPacket) {
        this.msSinceLastPacket = msSinceLastPacket;


        // Prevents the counter becoming too big
        if (this.msSinceLastPacket > 1000){
            addSecSinceLastPacket();
            this.msSinceLastPacket = 0;
        }
    }

    public int getSecSinceLastPacket() {
        return secSinceLastPacket;
    }

    public void setSecSinceLastPacket(int secSinceLastPacket) {
        this.secSinceLastPacket = secSinceLastPacket;
    }

    public void addSecSinceLastPacket() {
        this.secSinceLastPacket += 1;
    }

    public String getAlerts() {
        return alerts;
    }

    public void setAlerts(String Alerts) {
        alerts = Alerts;
    }


    public String getDriveMode() {
        if (idle) return "Idle";
        else if (reverse) return "R";
        else if (neutral) return "N";
        else if (drive) return "D";
        else return "None";
    }

    public boolean isBrakes() {
        return brakes;
    }

    public void setBrakes(boolean brakes) {
        this.brakes = brakes;
    }

    public boolean isSOMCruiseControl() {
        return SOMCruiseControl;
    }

    public void setSOMCruiseControl(boolean SOMCruiseControl) {
        this.SOMCruiseControl = SOMCruiseControl;
    }

    public boolean isCruiseControl() {
        return cruiseControl;
    }

    public boolean isSetPointCruiseControl() {
        return setPointCruiseControl;
    }

    public void setSetPointCruiseControl(boolean setPointCruiseControl) {
        setCruiseControl(setPointCruiseControl);
        this.setPointCruiseControl = setPointCruiseControl;
    }

    public boolean isSpeedCruiseControl() {
        return speedCruiseControl;
    }

    public void setSpeedCruiseControl(boolean speedCruiseControl) {
        setCruiseControl(speedCruiseControl);
        this.speedCruiseControl = speedCruiseControl;
    }

    public void setCruiseControl(boolean cruiseControl) {
        this.cruiseControl = cruiseControl;
    }

    public int getCruiseTargetSpeed() {
        return cruiseTargetSpeed;
    }

    public void setCruiseTargetSpeed(int cruiseTargetSpeed) {
        this.cruiseTargetSpeed = cruiseTargetSpeed;
    }

    public boolean isDrive() {
        return drive;
    }

    public void setDrive(boolean drive) {
        this.drive = drive;
    }

    public boolean isHazard() {
        return hazard;
    }

    public void setHazard(boolean hazard) {
        this.hazard = hazard;
    }

    public boolean isHorn() {
        return horn;
    }

    public void setHorn(boolean horn) {
        this.horn = horn;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public double getLastArray1Amps() {
        return lastArray1Amps;
    }

    public void setLastArray1Amps(double lastArray1Amps) {
        this.lastArray1Amps = lastArray1Amps;
    }

    public double getLastArray1Volts() {
        return lastArray1Volts;
    }

    public void setLastArray1Volts(double lastArray1Volts) {
        this.lastArray1Volts = lastArray1Volts;
    }

    public double getLastArray2Amps() {
        return lastArray2Amps;
    }

    public void setLastArray2Amps(double lastArray2Amps) {
        this.lastArray2Amps = lastArray2Amps;
    }

    public double getLastArray2Volts() {
        return lastArray2Volts;
    }

    public void setLastArray2Volts(double lastArray2Volts) {
        this.lastArray2Volts = lastArray2Volts;
    }

    public double getLastArray3Amps() {
        return lastArray3Amps;
    }

    public void setLastArray3Amps(double lastArray3Amps) {
        this.lastArray3Amps = lastArray3Amps;
    }

    public double getLastArray3Volts() {
        return lastArray3Volts;
    }

    public void setLastArray3Volts(double lastArray3Volts) {
        this.lastArray3Volts = lastArray3Volts;
    }

    public double getLastBatteryAmps() {
        return lastBatteryAmps;
    }

    public void setLastBatteryAmps(double lastBatteryAmps) {
        this.lastBatteryAmps = lastBatteryAmps;
    }

    public double getLastBatteryVolts() {
        return lastBatteryVolts;
    }

    public void setLastBatteryVolts(double lastBatteryVolts) {
        this.lastBatteryVolts = lastBatteryVolts;
    }

    public double getLastBusAmps() {
        return lastBusAmps;
    }

    public void setLastBusAmps(double lastBusAmps) {
        this.lastBusAmps = lastBusAmps;
    }

    public double getLastBusPower() {
        return lastBusPower;
    }

    public void setLastBusPower(double lastBusPower) {
        this.lastBusPower = lastBusPower;
    }

    public double getLastBusVolts() {
        return lastBusVolts;
    }

    public void setLastBusVolts(double lastBusVolts) {
        this.lastBusVolts = lastBusVolts;
    }

    public double getLastControllerTemp() {
        return lastControllerTemp;
    }

    public void setLastControllerTemp(double lastControllerTemp) {
        this.lastControllerTemp = lastControllerTemp;
    }

    public double getLastLockedSOC() {
        return lastLockedSOC;
    }

    public void setLastLockedSOC(double lastLockedSOC) {
        this.lastLockedSOC = lastLockedSOC;
    }

    public int getLastMaxCellTemp() {
        return lastMaxCellTemp;
    }

    public void setLastMaxCellTemp(int lastMaxCellTemp) {
        this.lastMaxCellTemp = lastMaxCellTemp;
    }

    public int getLastMaximumCellV() {
        return lastMaximumCellV;
    }

    public void setLastMaximumCellV(int lastMaximumCellV) {
        this.lastMaximumCellV = lastMaximumCellV;
    }

    public int getLastMinimumCellV() {
        return lastMinimumCellV;
    }

    public void setLastMinimumCellV(int lastMinimumCellV) {
        this.lastMinimumCellV = lastMinimumCellV;
    }

    public int getLastMotorPowerSetpoint() {
        return lastMotorPowerSetpoint;
    }

    public void setLastMotorPowerSetpoint(int lastMotorPowerSetpoint) {
        this.lastMotorPowerSetpoint = lastMotorPowerSetpoint;
    }

    public double getLastMotorTemp() {
        return lastMotorTemp;
    }

    public void setLastMotorTemp(double lastMotorTemp) {
        this.lastMotorTemp = lastMotorTemp;
    }

    public double getLastSOC() {
        return lastSOC;
    }

    public void setLastSOC(double lastSOC) {
        this.lastSOC = lastSOC;
    }

    public int getLastSpeed() {
        return lastSpeed;
    }

    public void setLastSpeed(int lastSpeed) {
        this.lastSpeed = lastSpeed;
    }

    public int getLastTwelveVBusVolts() {
        return lastTwelveVBusVolts;
    }

    public void setLastTwelveVBusVolts(int lastTwelveVBusVolts) {
        this.lastTwelveVBusVolts = lastTwelveVBusVolts;
    }

    public boolean isLeftBlinker() {
        return leftBlinker;
    }

    public void setLeftBlinker(boolean leftBlinker) {
        this.leftBlinker = leftBlinker;
    }

    public boolean isNeutral() {
        return neutral;
    }

    public void setNeutral(boolean neutral) {
        this.neutral = neutral;
    }

    public boolean isRegen() {
        return regen;
    }

    public void setRegen(boolean regen) {
        this.regen = regen;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public boolean isRightBlinker() {
        return rightBlinker;
    }

    public void setRightBlinker(boolean rightBlinker) {
        this.rightBlinker = rightBlinker;
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public double getLastArray1Power() {
        return lastArray1Volts * lastArray1Amps;
    }

    public double getLastArray2Power() {
        return lastArray2Volts * lastArray2Amps;
    }

    public double getLastArray3Power() {
        return lastArray3Volts * lastArray3Amps;
    }

    public double getLastArrayTotalPower() {
        return getLastArray1Power() + getLastArray2Power() + getLastArray3Power();
    }

    public boolean isTestLayout() {
        return testLayout;
    }

    public void setTestLayout(boolean testLayout) {
        this.testLayout = testLayout;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<ArrowMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<ArrowMessage> messageList) {
        this.messageList = messageList;
    }


}
