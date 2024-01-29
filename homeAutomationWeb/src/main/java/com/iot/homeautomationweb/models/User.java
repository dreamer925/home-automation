package com.iot.homeautomationweb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank
    private String name;
    @Email
    @Pattern(regexp=".+@.+\\..+", message="Email address is not valid.")
    @NotNull
    private String email;
    @NotBlank
    @Length(min = 8)
    private String password;
    @OneToMany
    private List<Light> lights = new ArrayList<>();
    @OneToMany
    private List<Device> devices = new ArrayList<>();
    @OneToOne
    private Curtains curtains;
    private boolean locked = true;
    private boolean enabled = false;

    public User(String name, String email, String password) {

        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void addLight(Light light){
        this.lights.add(light);
    }

    public void deleteLight(Light light){
        this.lights.remove(light);
    }

    public void addDevice(Device device){
        this.devices.add(device);
    }

    public void deleteDevice(Device device){
        this.devices.remove(device);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
