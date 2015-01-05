package au.com.teamarrow.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import au.com.teamarrow.model.MeasurementType;

public interface MeasurementTypeRepository extends PagingAndSortingRepository<MeasurementType, Long> {

}
