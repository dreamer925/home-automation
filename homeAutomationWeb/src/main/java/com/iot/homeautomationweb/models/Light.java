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
public class Light {
    @Id
    @GeneratedValue
    private int id;
    @NotBlank
    private String room;
    private String color = "none";
    private boolean state = false;


    public Light(String room, String color, boolean state) {
        this.room = room;
        this.color = color;
        this.state = state;
    }

    public Light(String room) {
        this.room = room;
    }
}
