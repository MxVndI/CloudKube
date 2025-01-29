package io.unitbean.controller;

import io.unitbean.model.User;
import io.unitbean.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    public String getLoginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginUser(@Validated User user) {
        authService.loginUser(user);
        return "user/profile";
    }

    @GetMapping("/register")
    public String getRegisterForm() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Validated User user) {
        authService.registerUser(user);
        return "auth/login";
    }
}
