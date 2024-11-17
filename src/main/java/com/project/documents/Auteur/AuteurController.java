package com.project.documents.Auteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

    @Controller
    @RequestMapping("/auteurs")
    public class AuteurController {
        @Autowired
        private AuteurService auteurService;

        @GetMapping
        public String listAuteurs(Model model) {
            model.addAttribute("auteurs", auteurService.getAllAuteurs());
            return "auteurs/listauteur";
        }

        @GetMapping("/add")
        public String addAuteurForm(Model model) {
            model.addAttribute("auteur", new Auteur());
            return "auteurs/addauteur";
        }

        @PostMapping("/add")
        public String addAuteur(@ModelAttribute Auteur auteur) {
            auteurService.saveAuteur(auteur);
            return "redirect:/auteurs";
        }

        @GetMapping("/edit/{id}")
        public String editAuteurForm(@PathVariable Long id, Model model) {
            Auteur auteur = auteurService.getAuteurById(id);
            model.addAttribute("auteur", auteur);
            return "auteurs/editauteur";
        }

        @PostMapping("/edit/{id}")
        public String editAuteur(@PathVariable Long id, @ModelAttribute Auteur auteur) {
            auteur.setId(id);
            auteurService.saveAuteur(auteur);
            return "redirect:/auteurs";
        }

        @GetMapping("/delete/{id}")
        public String deleteAuteur(@PathVariable Long id) {
            auteurService.deleteAuteur(id);
            return "redirect:/auteurs";
        }
    }


