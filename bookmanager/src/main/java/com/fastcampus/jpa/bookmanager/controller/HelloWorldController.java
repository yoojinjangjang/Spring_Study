package com.fastcampus.jpa.bookmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //rest api 요청을 받을 수 있다.

public class HelloWorldController {
    @GetMapping("/helloworld")
    public String helloWorld(){
        return "hello-world";
    }
}
