package com.example.etudiantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtudiantRequestDto {
    private String nom;
    private String prenom;
    private String cne;
    private Long idFiliere;
}
