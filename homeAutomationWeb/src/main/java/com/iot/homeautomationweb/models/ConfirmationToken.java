package com.iot.homeautomationweb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String token;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private LocalDateTime expires;
    private LocalDateTime confirmed;
    @NotNull
    @ManyToOne
    @JoinColumn
    private User user;

    public ConfirmationToken(String token, LocalDateTime created, LocalDateTime expires, User user) {
        this.token = token;
        this.created = created;
        this.expires = expires;
        this.user = user;
    }
}

