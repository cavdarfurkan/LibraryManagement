package com.cavdarfurkan.librarymanagement.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CustomPasswordEncoder implements PasswordEncoder {
    private DelegatingPasswordEncoder selectPasswordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("md4", new Md4PasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());

        int index = new Random().nextInt(encoders.size());
        String encoder = encoders.keySet().toArray()[index].toString();

        return new DelegatingPasswordEncoder(encoder, encoders);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return selectPasswordEncoder().encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return selectPasswordEncoder().matches(rawPassword, encodedPassword);
    }
}
