package com.project.documents.web.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;


@Controller
public class AuthController {
    
    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/documents";
        }
        return "login";
    }
    @GetMapping("/access-denied")
    public String getAccessDeniedPage(Model model) {
     
        model.addAttribute("error", "You are not allowed to access this page");
        return "errors";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.logout();
        return "redirect:/documents";
    }
}