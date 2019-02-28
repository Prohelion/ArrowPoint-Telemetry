package com.prohelion.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.prohelion.model.MeasurementData;

@Transactional(readOnly = true)
public interface MeasurementDataRepository extends JpaRepository<MeasurementData, Long> {

    Page<MeasurementData> findByDataPointCanId(Integer dataPointCanId, Pageable pageable);
   
    // Note this query can return duplicates when multiple records have exactly the same timestamp
    // This issue is dealt with in MeasurementDataServiceImpl.findLatestDataForCanId() at the moment as the sql query to deal with this
    // issue is likely to cause greater performance loss
    @Query(nativeQuery = true, value = "select md.* from msrmnt_data md inner join (select data_pnt_can_id as dpcid, max(tstamp) as maxt from msrmnt_data group by data_pnt_can_id) max_tstamps on md.data_pnt_can_id = max_tstamps.dpcid and md.tstamp = max_tstamps.maxt where md.data_pnt_can_id >> 4 = ?1 order by data_pnt_can_id DESC")    
    List<MeasurementData> findLatestDataForCanId(Integer canId);

}
