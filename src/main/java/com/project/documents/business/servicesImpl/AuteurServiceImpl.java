package com.project.documents.business.servicesImpl;

import com.project.documents.business.services.AuteurService;
import com.project.documents.dao.entities.Auteur;
import com.project.documents.dao.repositories.AuteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AuteurServiceImpl implements AuteurService {

     @Autowired
        private AuteurRepository auteurRepository;
    @Override
        public List<Auteur> getAllAuteurs() {
            return auteurRepository.findAll();
        }
    @Override
        public void saveAuteur(Auteur auteur) {
            auteurRepository.save(auteur);
        }
    @Override
        public Auteur getAuteurById(Long id) {
            return auteurRepository.findById(id).orElse(null);
        }
    @Override
        public void deleteAuteur(Long id) {
            auteurRepository.deleteById(id);
        }
    }


