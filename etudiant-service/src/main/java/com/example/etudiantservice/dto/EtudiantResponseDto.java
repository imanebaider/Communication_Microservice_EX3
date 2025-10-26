package com.example.etudiantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtudiantResponseDto {
    private Long idEtudiant;
    private String nom;
    private String prenom;
    private String cne;
    private FiliereResponseDto filiere; // info complète
}
