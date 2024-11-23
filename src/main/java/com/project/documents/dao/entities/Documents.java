package com.project.documents.dao.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public class Documents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String motsCles;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;
    private String resume;
    private LocalDate publicationDate;
    private String typeFichier;
    @ManyToOne
    @JoinColumn(name="fichier_id")
    private File fichier;
    @ManyToOne
    @JoinColumn(name = "auteur_id")
    private Auteur auteur;
}
