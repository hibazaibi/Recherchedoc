package com.project.documents.web.controllers;

import com.project.documents.dao.entities.Theme;
import com.project.documents.business.services.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Slf4j
@Controller
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;
    @GetMapping
    public String listThemes(Model model) {
        log.info("Fetching list of themes");
        model.addAttribute("themes", themeService.getAllThemes());
        return "themes/listthemes";
    }
    @GetMapping("/add")
    public String addThemeForm(Model model) {
        model.addAttribute("theme", new Theme());
        return "themes/addtheme";
    }
    @PostMapping("/add")
    public String saveTheme(@Valid @ModelAttribute("theme") Theme theme, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors while adding theme: {}", bindingResult.getAllErrors());
            return "themes/addtheme";
        }
        try {
            themeService.saveTheme(theme);
            redirectAttributes.addFlashAttribute("success", "Le thème a été ajouté avec succès !");
            log.info("Theme added successfully: {}", theme.getNom());
        } catch (Exception e) {
            log.error("Error while saving theme", e);
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout du thème.");
        }
        return "redirect:/themes";
    }

    @GetMapping("/edit/{id}")
    public String editThemeForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("theme", themeService.getThemeById(id));
        return "themes/edittheme";
    }

    @PostMapping("/edit/{id}")
    public String updateTheme(@PathVariable("id") Long id, @Valid @ModelAttribute("theme") Theme updatedTheme,
                              BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors while updating theme: {}", bindingResult.getAllErrors());
            model.addAttribute("theme", updatedTheme);
            return "themes/edittheme";
        }
        try {
            Theme existingTheme = themeService.getThemeById(id);
            existingTheme.setNom(updatedTheme.getNom());
            themeService.saveTheme(existingTheme);
            redirectAttributes.addFlashAttribute("success", "Théme mis à jour avec succès.");
            log.info("Theme updated successfully: {}", updatedTheme.getNom());
        } catch (Exception e) {
            log.error("Error while updating theme with ID: {}", id, e);
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour du thème.");
        }
        return "redirect:/themes";
    }
    @PostMapping("/delete/{id}")
    public String deleteTheme(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            themeService.deleteTheme(id);
            redirectAttributes.addFlashAttribute("success", "Thème supprimé avec succès.");
            log.info("Theme deleted successfully with ID: {}", id);
        } catch (Exception e) {
            log.error("Error while deleting theme with ID: {}", id, e);
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression du thème.");
        }
        return "redirect:/themes";
    }
}



