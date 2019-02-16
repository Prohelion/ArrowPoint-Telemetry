package com.prohelion.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.prohelion.model.Device;
import com.prohelion.model.Measurement;

@Transactional
public interface MeasurementRepository extends PagingAndSortingRepository<Measurement, Long> {

	@Cacheable("MeasureRepoCanSetupById")
    Measurement findByCanId(Integer canId);

    List<Measurement> findByDevice(Device d);
    
    List<Measurement> findAll();
}
