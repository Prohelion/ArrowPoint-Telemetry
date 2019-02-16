package com.prohelion.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prohelion.dao.DeviceRepository;
import com.prohelion.model.Device;
import com.prohelion.service.DeviceService;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable("devices")
    public List<Device> getDevices() {
        List<Device> targetCollection = new ArrayList<Device>();
        CollectionUtils.addAll(targetCollection, deviceRepository.findAll().iterator());
        
        return targetCollection;
    }
}
