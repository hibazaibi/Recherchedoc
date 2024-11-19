package com.project.documents.dao.repositories;

import com.project.documents.dao.entities.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface AuteurRepository extends JpaRepository<Auteur, Long> {
}
