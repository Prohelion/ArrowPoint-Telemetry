package com.prohelion.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.prohelion.model.DataPoint;
import com.prohelion.model.Measurement;

public interface DataPointRepository extends JpaRepository<DataPoint, Long> {

	@Cacheable("DataPointRepoCanSetupById")
    DataPoint findByDataPointCanId(Integer dataPointCanId);
    
	@Cacheable("DataPointRepoCanSetupByMeasurement")
    List<DataPoint> findByMeasurement(Measurement measurement);
}
