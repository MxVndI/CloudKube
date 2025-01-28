package io.unitbean.controller;

import io.unitbean.model.User;
import io.unitbean.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {
    private final RegistrationService registrationService;

    @GetMapping
    public String getRegisterForm() {
        return "auth/register";
    }

    @PostMapping
    public String registerUser(@Validated User user) {
        registrationService.registerUser(user);
        return "auth/login";
    }

    @GetMapping("/53")
    public String get52Form() {
        return "auth/ahaha";
    }
}
