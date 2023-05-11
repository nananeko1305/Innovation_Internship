package com.example.function2;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Function2Controller {

    @GetMapping("/test")
    public String test() {
        return "Hello from function2!";
    }

    @PostMapping(value = "/data",consumes = "application/json")
    public DataModel saveData(@RequestBody DataModel data) {
        System.out.println("Hi!");
        return data;
    }
}
