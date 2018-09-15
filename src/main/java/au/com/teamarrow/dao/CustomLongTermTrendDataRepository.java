package au.com.teamarrow.dao;

import java.time.OffsetDateTime;
import java.util.List;
import au.com.teamarrow.model.LongTermTrendData;

public interface CustomLongTermTrendDataRepository {

    List<LongTermTrendData> getTrendDataForDay(Integer deviceId, OffsetDateTime dateTime);

}
