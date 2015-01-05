package au.com.teamarrow.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import au.com.teamarrow.model.LongTermTrendData;

@Transactional(readOnly = true)
public interface LongTermTrendDataRepository extends JpaRepository<LongTermTrendData, Long>, CustomLongTermTrendDataRepository {

    Page<LongTermTrendData> findByDataPointCanId(Integer dataPointCanId, Pageable pageable);
    
    @Query(nativeQuery = true, value = "select lttd.* from msrmnt_data lttd inner join (select data_pnt_can_id as dpcid, max(tstamp) as maxt from msrmnt_data group by data_pnt_can_id) max_tstamps on lttd.data_pnt_can_id = max_tstamps.dpcid and lttd.tstamp = max_tstamps.maxt where lttd.data_pnt_can_id >> 4 = ?1")
    List<LongTermTrendData> findLatestDataForCanId(Integer canId);
}
