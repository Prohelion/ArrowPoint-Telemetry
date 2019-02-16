package com.prohelion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.prohelion.model.Device;

@Transactional
public interface DeviceService {

    List<Device> getDevices();
}
