package com.project.documents.controllers;


import com.project.documents.dao.entities.Theme;
import com.project.documents.services.DocumentsService;
import com.project.documents.dao.entities.Auteur;
import com.project.documents.services.AuteurService;
import com.project.documents.dao.entities.Documents;
import com.project.documents.dao.entities.File;
import com.project.documents.services.FileService;
import com.project.documents.services.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Controller
    @RequestMapping("/documents")
@RequiredArgsConstructor
    public class DocumentsController {
        @Autowired
        private DocumentsService documentService;

        @Autowired
        private AuteurService auteurService;
    @Autowired
    private ThemeService themeService;
    private final FileService fileService;


    @GetMapping
        public String listDocuments(Model model) {
            model.addAttribute("documents", documentService.getAllDocuments());
            model.addAttribute("auteurs", auteurService.getAllAuteurs());
        model.addAttribute("themes", themeService.getAllThemes());
            return "documents/listdoc";
        }

    @GetMapping("/add")
    public String showAddDocumentForm(Model model) {
        Documents document = new Documents();
        document.setAuteur(new Auteur());
        model.addAttribute("document", document);
        model.addAttribute("auteurs", auteurService.getAllAuteurs());
        model.addAttribute("themes", themeService.getAllThemes());
        return "documents/adddoc";
    }

    @PostMapping("/add")
    public String addDocument(@ModelAttribute Documents document,
                              @RequestParam("auteur.id") Long auteurId,
                              @RequestParam("theme.id") Long themeId,

                              @RequestParam("file") MultipartFile file) throws Exception {

        Auteur auteur = auteurService.getAuteurById(auteurId);
        document.setAuteur(auteur);
        Theme theme = themeService.getThemeById(themeId);
        document.setTheme(theme);

        if (!file.isEmpty()) {
            File attachment = fileService.saveAttachment(file);
            document.setFichier(attachment);
        }

        documentService.saveDocument(document);

        return "redirect:/documents";
    }


    @GetMapping("/edit/{id}")
    public String showEditDocumentForm(@PathVariable Long id, Model model) {
        Documents document = documentService.getDocumentById(id);
        model.addAttribute("document", document);
        model.addAttribute("auteurs", auteurService.getAllAuteurs());
        model.addAttribute("themes", themeService.getAllThemes());
        return "documents/editdoc";
    }

    @PostMapping("/edit/{id}")
    public String updateDocument(@PathVariable Long id,@RequestParam Long auteurId, @RequestParam Long themeId, @ModelAttribute Documents document) {
        Auteur auteur = auteurService.getAuteurById(auteurId);
        if (auteur != null) {
            document.setAuteur(auteur);
        } else {
            throw new RuntimeException("Auteur not found");
        }
        Theme theme = themeService.getThemeById(themeId);
        if (theme != null) {
            document.setTheme(theme);
        } else {
            throw new RuntimeException("Theme not found");
        }
        documentService.updateDocument(id, document);
        return "redirect:/documents";
    }

    @GetMapping("/delete/{id}")
    public String deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return "redirect:/documents";
    }
    @PostMapping("/search")
    public String searchDocuments(@RequestParam String titre,
                                  @RequestParam String motsCles,
                                  @RequestParam(required = false) Long themeId,
                                  @RequestParam(required = false) Long auteurId,
                                  Model model) {
        List<Documents> documents = documentService.searchDocuments(titre, motsCles, themeId, auteurId);
        model.addAttribute("documents", documents);
        model.addAttribute("auteurs", auteurService.getAllAuteurs());
        model.addAttribute("themes", themeService.getAllThemes());
        return "documents/listdoc";
    }

    }


