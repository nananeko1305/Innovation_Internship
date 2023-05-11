package com.innovation.lambdaTest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LambdaTestController {

    @GetMapping("/test")
    public String test() {
        return "Hello world!";
    }
}

