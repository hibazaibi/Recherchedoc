package com.project.documents.docs;


import com.project.documents.Auteur.Auteur;
import com.project.documents.Auteur.AuteurService;
import com.project.documents.file.File;
import com.project.documents.file.FileService;
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

    private final FileService fileService;


    @GetMapping
        public String listDocuments(Model model) {
            model.addAttribute("documents", documentService.getAllDocuments());
            model.addAttribute("auteurs", auteurService.getAllAuteurs());
            return "documents/listdoc";
        }

        @PostMapping("/search")
        public String searchDocuments(@RequestParam String titre, @RequestParam String motsCles,
                                      @RequestParam String theme, @RequestParam(required = false) Long auteurId, Model model) {
            List<Documents> documents = documentService.searchDocuments(titre, motsCles, theme, auteurId);
            model.addAttribute("documents", documents);
            model.addAttribute("auteurs", auteurService.getAllAuteurs());
            return "documents/listdoc";
        }
    @GetMapping("/add")
    public String showAddDocumentForm(Model model) {
        Documents document = new Documents();
        document.setAuteur(new Auteur());
        model.addAttribute("document", document);
        model.addAttribute("auteurs", auteurService.getAllAuteurs());
        return "documents/adddoc";
    }

    @PostMapping("/add")
    public String addDocument(@ModelAttribute Documents document,
                              @RequestParam("auteur.id") Long auteurId,
                              @RequestParam("file") MultipartFile file) throws Exception {

        Auteur auteur = auteurService.getAuteurById(auteurId);
        document.setAuteur(auteur);

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
        return "documents/editdoc";
    }

    @PostMapping("/edit/{id}")
    public String updateDocument(@PathVariable Long id, @ModelAttribute Documents document) {
        documentService.updateDocument(id, document);
        return "redirect:/documents";
    }

    @GetMapping("/delete/{id}")
    public String deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return "redirect:/documents";
    }
    }


