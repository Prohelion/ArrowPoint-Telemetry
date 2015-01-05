package au.com.teamarrow.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import au.com.teamarrow.model.MediumTermTrendData;

@Transactional(readOnly = true)
public interface MediumTermTrendDataRepository extends JpaRepository<MediumTermTrendData, Long> {

    Page<MediumTermTrendData> findByDataPointCanId(Integer dataPointCanId, Pageable pageable);
    
    @Query(nativeQuery = true, value = "select mttd.* from msrmnt_data mttd inner join (select data_pnt_can_id as dpcid, max(tstamp) as maxt from msrmnt_data group by data_pnt_can_id) max_tstamps on mttd.data_pnt_can_id = max_tstamps.dpcid and mttd.tstamp = max_tstamps.maxt where mttd.data_pnt_can_id >> 4 = ?1")
    List<MediumTermTrendData> findLatestDataForCanId(Integer canId);
}
