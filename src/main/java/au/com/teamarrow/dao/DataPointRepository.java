package au.com.teamarrow.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import au.com.teamarrow.model.DataPoint;
import au.com.teamarrow.model.Measurement;

public interface DataPointRepository extends JpaRepository<DataPoint, Long> {

	@Cacheable("DataPointRepoCanSetupById")
    DataPoint findByDataPointCanId(Integer dataPointCanId);
    
	@Cacheable("DataPointRepoCanSetupByMeasurement")
    List<DataPoint> findByMeasurement(Measurement measurement);
}
