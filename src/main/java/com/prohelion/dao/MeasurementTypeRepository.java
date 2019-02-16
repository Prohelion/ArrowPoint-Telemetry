package com.prohelion.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.prohelion.model.MeasurementType;

public interface MeasurementTypeRepository extends PagingAndSortingRepository<MeasurementType, Long> {

}
