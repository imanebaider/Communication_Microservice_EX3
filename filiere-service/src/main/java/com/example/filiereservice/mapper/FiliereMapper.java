package com.example.filiereservice.mapper;

import com.example.filiereservice.dto.FiliereRequestDto;
import com.example.filiereservice.dto.FiliereResponseDto;
import com.example.filiereservice.entities.Filiere;
import org.springframework.stereotype.Component;

@Component
public class FiliereMapper {
    public Filiere dtoToEntity(FiliereRequestDto dto) {
        return new Filiere(null, dto.getCode(), dto.getLibelle());
    }

    public FiliereResponseDto entityToDto(Filiere filiere) {
        return new FiliereResponseDto(filiere.getIdFiliere(), filiere.getCode(), filiere.getLibelle());
    }
}
