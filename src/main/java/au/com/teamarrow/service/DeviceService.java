package au.com.teamarrow.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import au.com.teamarrow.model.Device;

@Transactional
public interface DeviceService {

    List<Device> getDevices();
}
