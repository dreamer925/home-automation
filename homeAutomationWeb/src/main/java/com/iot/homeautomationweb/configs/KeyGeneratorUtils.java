package com.iot.homeautomationweb.configs;

import lombok.NoArgsConstructor;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

@NoArgsConstructor
public class KeyGeneratorUtils {
    static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }
}
