package com.example.northwindwebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String Home(){
        return "homepage.html";
    }
    @GetMapping("/login")
    public String loginForm(){
        return "security/login";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "security/accessDenied";
    }

    @GetMapping ("/logout")
    public String logout(){
        return "security/logout";
    }

}
