package com.project.documents.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String redirectToDocuments() {
        return "redirect:/documents";
    }
}
