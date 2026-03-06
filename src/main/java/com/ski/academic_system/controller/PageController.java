package com.ski.academic_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/page")
@RequiredArgsConstructor
public class PageController {

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @GetMapping("/admin")
    public String adminPage() {
    	
    	return "admin";
    }
    
    @GetMapping("/professor")
    public String professorPage() {
    	return "professor";
    }
    

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
	
}