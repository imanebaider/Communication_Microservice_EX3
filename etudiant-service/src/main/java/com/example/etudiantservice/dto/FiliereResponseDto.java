package com.example.etudiantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiliereResponseDto {
    private Long idFiliere;
    private String code;
    private String libelle;
}
