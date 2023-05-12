package com.example.function1;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Function1Controller {

    @GetMapping("")
    public String test() {
        System.out.println("Console line function 1.");
        return "Hello from function1!";
    }

    @PostMapping(value = "",consumes = "application/json")
    public DataModel saveData(@RequestBody DataModel data) {
        System.out.println("Hi!");
        return data;
    }
}

