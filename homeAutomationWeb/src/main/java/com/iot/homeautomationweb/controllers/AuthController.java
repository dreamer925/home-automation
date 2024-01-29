package com.iot.homeautomationweb.controllers;

import com.iot.homeautomationweb.configs.TokenService;
import com.iot.homeautomationweb.models.LoginRequest;
import com.iot.homeautomationweb.models.User;
import com.iot.homeautomationweb.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/auth/login")
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponse token(@Valid @RequestBody LoginRequest userLogin, BindingResult result) throws AuthenticationException {
        if(result.hasErrors()){
            result.getFieldErrors().forEach(fieldError -> {
                System.out.println(fieldError.getField() + " " + fieldError.getDefaultMessage());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldError.getDefaultMessage());
            });
        }
        if (userRepository.findByEmail(userLogin.username()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User with email " + userLogin.username() +
                    " was not found.");
        }
        User user = userRepository.findByEmail(userLogin.username()).get();
        user.setEnabled(true);
        System.out.println(userLogin.username() + " " + userLogin.password());

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));

        System.out.println(authentication.getName() + " " + authentication.isAuthenticated());
        return new AuthResponse(tokenService.generateToken(authentication),
                user.getName());
    }

    record AuthResponse(String token, String name){}
}
