package com.iot.homeautomationweb;

import com.iot.homeautomationweb.models.User;
import com.iot.homeautomationweb.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class HomeAutomationWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeAutomationWebApplication.class, args);
    }

    @Bean
    CommandLineRunner addUs(UserRepository userRepository) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return args -> {
            User user = new User("Mary", "em@mail.com", passwordEncoder.encode("password"));
            user.setLocked(false);
            userRepository.save(user);
        };
    }

}
