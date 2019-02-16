package com.prohelion.model;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
public abstract class AbstractMeasurementData implements java.io.Serializable {

	private static final long serialVersionUID = 1083257021369869222L;
	
	protected long id;
    private Integer dataPointCanId;
    private OffsetDateTime timestamp;
    private Boolean extended;
    private Boolean retry;
    private Integer length;
    private Double floatValue;
    private Integer integerValue;
    private String charValue;
    private String state;

    public AbstractMeasurementData() {
    }

    public AbstractMeasurementData(Integer dataPointCanId, OffsetDateTime timestamp, Boolean extended, Boolean retry,
        Integer length, Double floatValue, Integer integerValue, String charValue, String state) {
        this.dataPointCanId = dataPointCanId;
        this.timestamp = timestamp;
        this.extended = extended;
        this.retry = retry;
        this.length = length;
        this.floatValue =floatValue;
        this.integerValue = integerValue;
        this.charValue = charValue;
        this.state = state;
    }

    @Transient
    public abstract long getId();

    public void setId(long Id) {
        this.id = Id;
    }

    @Column(name = "data_pnt_can_id")
    @JsonProperty(value = "cId")
    public Integer getDataPointCanId() {
        return this.dataPointCanId;
    }

    public void setDataPointCanId(Integer dataPointCanId) {
        this.dataPointCanId = dataPointCanId;
    }

    
    @Column(name = "tstamp", length = 29)  
    public OffsetDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Column(name = "extd")
    @JsonProperty(value = "extd")
    public Boolean getExtended() {
        return this.extended;
    }

    public void setExtended(Boolean extended) {
        this.extended = extended;
    }

    @Column(name = "rtr")
    @JsonProperty(value = "rtr")
    public Boolean getRetry() {
        return this.retry;
    }

    public void setRetry(Boolean retry) {
        this.retry = retry;
    }

    @Column(name = "data_len")
    @JsonProperty(value = "len")
    public Integer getLength() {
        return this.length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Column(name = "fval", precision = 17, scale = 17)
    @JsonProperty(value = "fv")
    public Double getFloatValue() {
        return this.floatValue;
    }

    public void setFloatValue(Double floatValue) {
        this.floatValue = floatValue;
    }

    @Column(name = "ival")
    @JsonProperty(value = "iv")
    public Integer getIntegerValue() {
        return this.integerValue;
    }

    public void setIntegerValue(Integer integerValue) {
        this.integerValue = integerValue;
    }

    @Column(name = "cval", length = 16)
    @JsonProperty(value = "cv")
    public String getCharValue() {
        return this.charValue;
    }

    public void setCharValue(String charValue) {
        this.charValue = charValue;
    }

    @Column(name = "state", length = 10)
    public String getState() {
    	// Added this check as it is possible for it not to be set if it
    	// has been constructed poorly.  Not having a state violates the constraints
    	// in the database table
    	if (this.state == null || this.state.equals("")) return("Normal");    	
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " [id=" + id + ", dataPointCanId=" + dataPointCanId + ", timestamp=" + timestamp +
            ", floatValue=" + floatValue + ", state=" + state + "]";
    }
}
