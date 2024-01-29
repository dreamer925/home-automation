package com.iot.homeautomationweb.controllers;

import com.iot.homeautomationweb.models.RegistrationRequest;
import com.iot.homeautomationweb.services.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/auth/signup")
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public String register(@Valid @RequestBody RegistrationRequest request, BindingResult result) {
        System.out.println(request);
        if(result.hasErrors()){
            result.getFieldErrors().forEach(fieldError -> {
                System.out.println(fieldError.getField() + " " + fieldError.getDefaultMessage());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldError.getDefaultMessage());
            });
        }
        return registrationService.register(request);
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {

        return registrationService.confirmToken(token);
    }
}
