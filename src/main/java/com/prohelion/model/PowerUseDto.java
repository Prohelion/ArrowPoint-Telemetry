package com.prohelion.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude
public class PowerUseDto implements Serializable {
    
    private static final long serialVersionUID = 1171587522064888992L;

    private OffsetDateTime timestamp;
    
    private float volts;
    
    private float amps;
    
    private float power;
    
    public PowerUseDto() {
        
    }

    @Column(name = "tstamp")
    @DateTimeFormat(pattern = "dd/MM/yy HH:mm:ss")
    @JsonFormat(pattern = "dd/MM/yy HH:mm:ss")
    @JsonProperty(value = "ts")
    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty(value = "volts")
    public float getVolts() {
        return volts;
    }

    public void setVolts(float volts) {
        this.volts = volts;
    }

    @JsonProperty(value = "amps")
    public float getAmps() {
        return amps;
    }
   
    public void setAmps(float amps) {
        this.amps = amps;
    }

    @JsonProperty(value = "fval")
    public float getPower() {
        return power;
    }
   
    public void setPower(float power) {
        this.power = power;
    }
}
