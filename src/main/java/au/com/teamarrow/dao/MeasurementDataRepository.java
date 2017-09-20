package au.com.teamarrow.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import au.com.teamarrow.model.MeasurementData;
import au.com.teamarrow.model.PowerUseDto;

@Transactional(readOnly = true)
public interface MeasurementDataRepository extends JpaRepository<MeasurementData, Long> {

    Page<MeasurementData> findByDataPointCanId(Integer dataPointCanId, Pageable pageable);
   
    @Query(nativeQuery = true, value = "select md.* from msrmnt_data md inner join (select data_pnt_can_id as dpcid, max(tstamp) as maxt from msrmnt_data group by data_pnt_can_id) max_tstamps on md.data_pnt_can_id = max_tstamps.dpcid and md.tstamp = max_tstamps.maxt where md.data_pnt_can_id >> 4 = ?1 order by data_pnt_can_id DESC")    
    List<MeasurementData> findLatestDataForCanId(Integer canId);

    @Query(nativeQuery = true, value = "select volt.tstamp as tstamp, volt.fval as volts, amp.fval as amps, (volt.fval * amp.fval)/1000000 as power from sht_term_trend_data volt, sht_term_trend_data amp where volt.tstamp = amp.tstamp and (volt.data_pnt_can_id = 28576 and amp.data_pnt_can_id = 28580) order by volt.tstamp")
    List<PowerUseDto> findShortTermPowerUseData();
    
    @Query(nativeQuery = true, value = "select volt.tstamp as tstamp, volt.fval as volts, amp.fval as amps, (volt.fval * amp.fval)/1000000 as power from med_term_trend_data volt, med_term_trend_data amp where volt.tstamp = amp.tstamp and (volt.data_pnt_can_id = 28576 and amp.data_pnt_can_id = 28580) order by volt.tstamp")
    List<PowerUseDto> findMediumTermPowerUseData();

    @Query(nativeQuery = true, value = "select volt.tstamp as tstamp, volt.fval as volts, amp.fval as amps, (volt.fval * amp.fval)/1000000 as power from lng_term_trend_data volt, lng_term_trend_data amp where volt.tstamp = amp.tstamp and (volt.data_pnt_can_id = 28576 and amp.data_pnt_can_id = 28580) order by volt.tstamp")
    List<PowerUseDto> findLongTermPowerUseData();
}
