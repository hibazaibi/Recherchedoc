package com.project.documents.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice

public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, RedirectAttributes redirectAttributes) {
        log.error("An error occurred: {}", ex.getMessage(), ex);
        redirectAttributes.addFlashAttribute("error", "Une erreur est survenue: " + ex.getMessage());
        return "redirect:/documents";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, RedirectAttributes redirectAttributes) {
        log.warn("Invalid argument: {}", ex.getMessage(), ex);
        redirectAttributes.addFlashAttribute("error", "Argument invalide: " + ex.getMessage());
        return "redirect:/documents";
    }
}

