package com.project.documents.web.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentForm {
    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    private String motsCles;

    @NotNull(message = "Le thème est obligatoire")
    private Long themeId;

    @NotNull(message = "L'auteur est obligatoire")
    private Long auteurId;
    @NotBlank(message = "Le type de fichier est obligatoire")
    private String typeFichier;
    @NotBlank(message = "Le résumé est obligatoire")
    @Size(max = 500, message = "Le résumé ne peut pas dépasser 500 caractères")
    private String resume;

    @NotNull(message = "La date de publication est obligatoire")
    @PastOrPresent(message = "La date ne peut pas être dans le futur")
    private LocalDate publicationDate;

    private MultipartFile fichier;
}
