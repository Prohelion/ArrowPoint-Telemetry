package au.com.teamarrow.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import au.com.teamarrow.model.DeviceType;

public interface DeviceTypeRepository extends PagingAndSortingRepository<DeviceType, Long> {

}
