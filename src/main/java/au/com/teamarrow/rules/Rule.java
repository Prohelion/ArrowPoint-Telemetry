package au.com.teamarrow.rules;

import java.util.List;

import au.com.teamarrow.model.MeasurementData;

public interface Rule {
	
	public String getRuleName();
	
	public String getMessage();
	
	public boolean isFailed();
	
	public boolean isStateChanged();
	
	public Integer getCanId();
	
	public void setMeasurementData(MeasurementData measurementData);
	
	void execute();

}
