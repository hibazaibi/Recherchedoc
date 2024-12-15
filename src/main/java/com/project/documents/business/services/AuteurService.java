package com.project.documents.business.services;

import com.project.documents.dao.entities.Auteur;

import java.util.List;

public interface AuteurService {
        List<Auteur> getAllAuteurs();
        void saveAuteur(Auteur auteur);
        Auteur getAuteurById(Long id);
        void deleteAuteur(Long id);
    }

