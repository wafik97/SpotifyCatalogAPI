package com.example.catalog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class HelloController {


    @GetMapping("/")
    public String home() throws IOException {
        return "Hello from API! There is no UI here...";
    }

}