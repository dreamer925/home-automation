package com.iot.homeautomationweb.repositories;

public interface EmailSender {
    void send(String to, String email);
}
