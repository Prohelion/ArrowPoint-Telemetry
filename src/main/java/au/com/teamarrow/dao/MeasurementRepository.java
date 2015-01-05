package au.com.teamarrow.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import au.com.teamarrow.model.Device;
import au.com.teamarrow.model.Measurement;

@Transactional
public interface MeasurementRepository extends PagingAndSortingRepository<Measurement, Long> {

	@Cacheable("MeasureRepoCanSetupById")
    Measurement findByCanId(Integer canId);

    List<Measurement> findByDevice(Device d);
}
