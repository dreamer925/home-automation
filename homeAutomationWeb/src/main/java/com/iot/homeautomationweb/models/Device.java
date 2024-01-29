package com.iot.homeautomationweb.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    @Id
    @GeneratedValue
    private int id;
    @NotBlank
    private String type;
    private boolean state = false;

    public Device(String type, boolean state) {
        this.type = type;
        this.state = state;
    }

    public Device(String type) {
        this.type = type;
    }
}
