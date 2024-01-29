package com.iot.homeautomationweb.controllers;

import com.iot.homeautomationweb.models.Curtains;
import com.iot.homeautomationweb.models.Device;
import com.iot.homeautomationweb.models.Light;
import com.iot.homeautomationweb.services.ApplianceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@EnableScheduling
@RequiredArgsConstructor
@RequestMapping(path = "api/appliance")
public class ApplianceController {

    private final ApplianceService applianceService;
    final String timePattern = "^(0?[1-9]|1[0-2]):[0-5][0-9] [AP]M$";

    @GetMapping("/lights")
    public List<Light> getUserLights() {
        if (applianceService.getUserLights().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return applianceService.getUserLights();
    }

    @GetMapping("/devices")
    public List<Device> getUserDevices() {
        if (applianceService.getUserDevices().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return applianceService.getUserDevices();
    }

    @GetMapping("/curtains")
    public Curtains getUserCurtains() {
        if (applianceService.getUserCurtains() == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return applianceService.getUserCurtains();
    }

    @PostMapping(value = "/state")
    public ResponseEntity<String> changeState(@RequestBody Map<String, String> request) {
        System.out.println(request.get("id") + " " + request.get("type") + " " + request.get("value"));
        int id = Integer.parseInt(request.get("id"));
        boolean value = Boolean.parseBoolean(request.get("value"));
        switch (request.get("type")) {
            case "light" -> applianceService.changeLightState(id, value);
            case "device" -> applianceService.changeDeviceState(id, value);
            case "curtains" -> applianceService.changeCurtainsState(value);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("State has been changed successfully.");
    }

    @PostMapping("/lights")
    public ResponseEntity<String> addLight(@Valid @RequestBody Light light, BindingResult bindingResult) {
        System.out.println(light);
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> {
                System.out.println(fieldError.getField() + " " + fieldError.getDefaultMessage());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldError.getDefaultMessage());
            });
        }
        if (applianceService.saveLight(light)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("New light has been added successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Light was not added.");
    }

    @PostMapping("/devices")
    public ResponseEntity<String> addDevice(@Valid @RequestBody Device device, BindingResult bindingResult) {
        System.out.println(device);
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> {
                System.out.println(fieldError.getField() + " " + fieldError.getDefaultMessage());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldError.getDefaultMessage());
            });
        }
        if (applianceService.saveDevice(device)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("New device has been added successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Light was not added.");
    }

    @PostMapping("/curtains")
    public ResponseEntity<String> addCurtains() {
        System.out.println("curtains");
        if (applianceService.saveCurtains()) {
            return ResponseEntity.status(HttpStatus.CREATED).body("New device has been added successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Light was not added.");
    }

    @PostMapping("/automation")
    public ResponseEntity<String> addAutomation(@RequestBody Map<String, @Pattern(regexp = timePattern) String> request,
                                                BindingResult bindingResult) {
        System.out.println("automation");
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> {
                System.out.println(fieldError.getField() + " " + fieldError.getDefaultMessage());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldError.getDefaultMessage());
            });
        }
        applianceService.changeAutomation(request.get("openTime"), request.get("closeTime"));
        return ResponseEntity.status(HttpStatus.CREATED).body("Automation has been added successfully.");
    }

    @DeleteMapping("/lights/{id}")
    public ResponseEntity<String> deleteLight(@PathVariable Integer id) {
        applianceService.deleteLightById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("Light with id = " + id + "has been deleted.");
    }

    @DeleteMapping("/devices/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable Integer id) {
        applianceService.deleteDeviceById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("Device with id = " + id + "has been deleted.");
    }

    @DeleteMapping("/curtains")
    public ResponseEntity<String> deleteCurtains() {
        applianceService.deleteCurtains();
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("Curtains were deleted.");
    }
}
