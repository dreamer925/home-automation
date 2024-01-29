package com.iot.homeautomationweb.repositories;

import com.iot.homeautomationweb.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    Device findDeviceByType(String type);
}
