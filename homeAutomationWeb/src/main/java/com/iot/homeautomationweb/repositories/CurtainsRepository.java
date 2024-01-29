package com.iot.homeautomationweb.repositories;

import com.iot.homeautomationweb.models.Curtains;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurtainsRepository extends JpaRepository <Curtains, Integer> {
}
