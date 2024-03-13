package com.vk.restapiproxy.form;

import com.vk.restapiproxy.database.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public record RegistrationForm(String username, String password) {

    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return user;
    }
}