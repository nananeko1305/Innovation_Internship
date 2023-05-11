package com.example.function1;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Function1Controller {

    @GetMapping("/test")
    public String test() {
        return "Hello from function1!";
    }

    @PostMapping(value = "/data",consumes = "application/json")
    public DataModel saveData(@RequestBody DataModel data) {
        System.out.println("Hi!");
        return data;
    }
}

