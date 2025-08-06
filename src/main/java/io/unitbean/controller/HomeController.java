package io.unitbean.controller;


import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
    @GetMapping
    public String getHomePage() {
        return "index";
    }
}
