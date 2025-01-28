package io.unitbean.controller;

import io.unitbean.model.User;
import io.unitbean.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    @GetMapping
    public String getLoginForm() {
        return "auth/login";
    }

    @PostMapping
    public String loginUser(@Validated User user) {
        loginService.loginUser(user);
        return "user/profile";
    }
}
