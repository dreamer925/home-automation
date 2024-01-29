package com.iot.homeautomationweb.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Curtains {
    @Id
    @GeneratedValue
    private int id;
    private Time openTime;
    private Time closeTime;
    private boolean state = false;

    public void setOpenTime(String openTimeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        try {
            Date parsedDate = sdf.parse(openTimeStr);
            this.openTime = new Time(parsedDate.getTime());
        } catch (ParseException e) {
            // Handle parsing error
            System.out.println("ParseException: " + e);
        }
    }

    public void setCloseTime(String closeTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        try {
            Date parsedDate = sdf.parse(closeTime);
            this.closeTime = new Time(parsedDate.getTime());
        } catch (ParseException e) {
            // Handle parsing error
            System.out.println("ParseException: " + e);
        }
    }
}
