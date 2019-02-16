package com.prohelion.dao;

import java.time.OffsetDateTime;
import java.util.List;

import com.prohelion.model.LongTermTrendData;

public interface CustomLongTermTrendDataRepository {

    List<LongTermTrendData> getTrendDataForDay(Integer deviceId, OffsetDateTime dateTime);

}
