package com.example.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

    @GetMapping("/")
    public String home() {
        return "Backend is working 🚀";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello Khaled 👋";
    }
}