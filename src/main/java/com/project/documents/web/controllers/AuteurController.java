package com.project.documents.web.controllers;
import com.project.documents.business.services.AuteurService;
import com.project.documents.dao.entities.Auteur;
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
    @RequestMapping("/auteurs")
    @RequiredArgsConstructor
    public class AuteurController {

        private final AuteurService auteurService;

        @GetMapping
        public String listAuteurs(Model model) {
            log.info("Fetching list of authors");
            model.addAttribute("auteurs", auteurService.getAllAuteurs());
            return "auteurs/listauteur";
        }
        @GetMapping("/add")
        public String addAuteurForm(Model model) {
            model.addAttribute("auteur", new Auteur());
            return "auteurs/addauteur";
        }

        @PostMapping("/add")
        public String addAuteur(@Valid @ModelAttribute("auteur") Auteur auteur,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                Model model) {
            if (bindingResult.hasErrors()) {
                log.warn("Validation errors while adding author: {}", bindingResult.getAllErrors());
                return "auteurs/addauteur";
            }
            try {
                auteurService.saveAuteur(auteur);
                redirectAttributes.addFlashAttribute("success", "L'auteur a été ajouté avec succès !");
                log.info("Author added successfully: {}", auteur.getNom());
            } catch (Exception e) {
                log.error("Error while saving author", e);
                redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout de l'auteur.");
            }
            return "redirect:/auteurs";
        }


    @GetMapping("/edit/{id}")
        public String editAuteurForm(@PathVariable Long id, Model model) {
            Auteur auteur = auteurService.getAuteurById(id);
            model.addAttribute("auteur", auteur);
            return "auteurs/editauteur";
        }

        @PostMapping("/edit/{id}")
        public String editAuteur(@PathVariable Long id, @Valid @ModelAttribute("auteur") Auteur auteur, BindingResult bindingResult, Model model,RedirectAttributes redirectAttributes) {
            if (bindingResult.hasErrors()) {
                log.warn("Validation errors while updating author: {}", bindingResult.getAllErrors());
                return "auteurs/editauteur";
            }
            try {
                auteur.setId(id);
                auteurService.saveAuteur(auteur);
                redirectAttributes.addFlashAttribute("success", "Auteur mis à jour avec succès.");
                log.info("Author updated successfully: {}", auteur.getNom());
            } catch (Exception e) {
                log.error("Error while updating author with ID: {}", id, e);
                redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour de l'auteur.");
            }
            return "redirect:/auteurs";
        }

    @GetMapping("/delete/{id}")
    public String deleteAuteur(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            auteurService.deleteAuteur(id);
            redirectAttributes.addFlashAttribute("success", "Auteur supprimé avec succès.");
            log.info("Author deleted successfully with ID: {}", id);
        } catch (Exception e) {
            log.error("Error while deleting author with ID: {}", id, e);
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression de l'auteur.");
        }
        return "redirect:/auteurs";
    }
    }


