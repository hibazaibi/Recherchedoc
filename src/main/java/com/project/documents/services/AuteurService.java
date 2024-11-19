package com.project.documents.services;
import com.project.documents.dao.entities.Auteur;
import com.project.documents.dao.repositories.AuteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AuteurService {

     @Autowired
        private AuteurRepository auteurRepository;

        public List<Auteur> getAllAuteurs() {
            return auteurRepository.findAll();
        }

        public void saveAuteur(Auteur auteur) {
            auteurRepository.save(auteur);
        }

        public Auteur getAuteurById(Long id) {
            return auteurRepository.findById(id).orElse(null);
        }

        public void deleteAuteur(Long id) {
            auteurRepository.deleteById(id);
        }
    }


