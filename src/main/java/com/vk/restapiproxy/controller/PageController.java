package com.vk.restapiproxy.controller;

import com.vk.restapiproxy.form.RegistrationForm;
import com.vk.restapiproxy.database.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registerForm() {
        return "RegisterForm";
    }

    @RequestMapping(value = {"/home", ""}, method = {RequestMethod.GET, RequestMethod.POST})
    public String homePage() {
        return "HomePage";
    }

    @RequestMapping(value = "/register-denied", method = {RequestMethod.GET})
    public String registerDenied() {
        return "RegisterDenied";
    }

    @PostMapping("/register")
    public String processRegistration(RegistrationForm registrationForm) {
        userService.save(registrationForm.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
