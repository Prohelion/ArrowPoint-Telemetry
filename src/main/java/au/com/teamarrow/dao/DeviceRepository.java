package au.com.teamarrow.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import au.com.teamarrow.model.Device;

@Transactional
public interface DeviceRepository extends PagingAndSortingRepository<Device, Long> {

}
