package com.iot.homeautomationweb.repositories;

import com.iot.homeautomationweb.models.Light;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LightRepository extends JpaRepository<Light, Integer> {
    Light findLightByRoom (String room);
    Light findLightByColor (String color);
}
