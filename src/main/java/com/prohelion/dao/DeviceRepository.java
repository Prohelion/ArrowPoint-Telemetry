package com.prohelion.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.prohelion.model.Device;

@Transactional
public interface DeviceRepository extends PagingAndSortingRepository<Device, Long> {

}
