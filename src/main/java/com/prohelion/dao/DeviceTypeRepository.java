package com.prohelion.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.prohelion.model.DeviceType;

public interface DeviceTypeRepository extends PagingAndSortingRepository<DeviceType, Long> {

}
