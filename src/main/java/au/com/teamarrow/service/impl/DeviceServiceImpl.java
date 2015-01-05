package au.com.teamarrow.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.teamarrow.dao.DeviceRepository;
import au.com.teamarrow.model.Device;
import au.com.teamarrow.service.DeviceService;

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
