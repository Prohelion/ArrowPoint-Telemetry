package com.prohelion.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.prohelion.model.ShortTermTrendData;

@Transactional(readOnly = true)
public interface ShortTermTrendDataRepository extends JpaRepository<ShortTermTrendData, Long> {

    Page<ShortTermTrendData> findByDataPointCanId(Integer dataPointCanId, Pageable pageable);
    
    @Query(nativeQuery = true, value = "select sttd.* from sht_term_trend_data sttd inner join (select data_pnt_can_id as dpcid, max(tstamp) as maxt from msrmnt_data group by data_pnt_can_id) max_tstamps on sttd.data_pnt_can_id = max_tstamps.dpcid and sttd.tstamp = max_tstamps.maxt where sttd.data_pnt_can_id >> 4 = ?1")
    List<ShortTermTrendData> findLatestDataForCanId(Integer canId);
}
