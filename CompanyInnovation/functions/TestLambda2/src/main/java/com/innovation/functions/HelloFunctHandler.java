package com.innovation.functions;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloFunctHandler {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }

    @PostMapping(value = "/data",consumes = "application/json")
    public DataModel saveData(@RequestBody DataModel data) {
        System.out.println("Hi!");
        return data;
    }
}