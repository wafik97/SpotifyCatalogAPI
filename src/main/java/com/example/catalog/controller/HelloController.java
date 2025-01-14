package com.example.catalog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "Hello from API! There is no UI here...";
    }

    @GetMapping("/internal")
    public String home1() {
        return "internal Hello from API! There is no UI here...";
    }

}


// hi there im here