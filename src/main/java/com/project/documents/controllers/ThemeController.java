package com.project.documents.controllers;

import com.project.documents.dao.entities.Theme;
import com.project.documents.services.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;
    @GetMapping
    public String listThemes(Model model) {
        model.addAttribute("themes", themeService.getAllThemes());
        return "themes/listthemes";
    }
    @GetMapping("/add")
    public String addThemeForm(Model model) {
        model.addAttribute("theme", new Theme());
        return "themes/addtheme";
    }
    @PostMapping("/add")
    public String saveTheme(@ModelAttribute("theme") Theme theme) {
        themeService.saveTheme(theme);
        return "redirect:/themes";
    }
    @GetMapping("/edit/{id}")
    public String editThemeForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("theme", themeService.getThemeById(id));
        return "themes/edittheme";
    }
    @PostMapping("/edit/{id}")
    public String updateTheme(@PathVariable("id") Long id, @ModelAttribute("theme") Theme updatedTheme) {
        Theme existingTheme = themeService.getThemeById(id);
        existingTheme.setNom(updatedTheme.getNom());
        themeService.saveTheme(existingTheme);
        return "redirect:/themes";
    }
    @GetMapping("/delete/{id}")
    public String deleteTheme(@PathVariable("id") Long id) {
        themeService.deleteTheme(id);
        return "redirect:/themes";
    }

}
