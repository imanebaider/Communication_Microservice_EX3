package com.example.etudiantservice.mapper;

import com.example.etudiantservice.dto.EtudiantRequestDto;
import com.example.etudiantservice.dto.EtudiantResponseDto;
import com.example.etudiantservice.dto.FiliereResponseDto;
import com.example.etudiantservice.entities.Etudiant;
import org.springframework.stereotype.Component;
import lombok.Data;
@Data
@Component
public class EtudiantMapper {
    public Etudiant dtoToEntity(EtudiantRequestDto dto) {
        return new Etudiant(null, dto.getNom(), dto.getPrenom(), dto.getCne(), dto.getIdFiliere());
    }

    public EtudiantResponseDto entityToDto(Etudiant etudiant, FiliereResponseDto filiere) {
        return new EtudiantResponseDto(etudiant.getIdEtudiant(), etudiant.getNom(),
                etudiant.getPrenom(), etudiant.getCne(), filiere);
    }
}
