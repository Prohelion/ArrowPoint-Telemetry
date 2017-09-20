package au.com.teamarrow.dao;

import java.util.List;

import org.joda.time.DateTime;
import au.com.teamarrow.model.LongTermTrendData;


public interface CustomLongTermTrendDataRepository {

    List<LongTermTrendData> getTrendDataForDay(Integer deviceId, DateTime dateTime);

}
