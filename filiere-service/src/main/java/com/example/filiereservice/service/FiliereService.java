package com.example.filiereservice.service;

import com.example.filiereservice.dto.FiliereRequestDto;
import com.example.filiereservice.dto.FiliereResponseDto;
import com.example.filiereservice.entities.Filiere;
import com.example.filiereservice.mapper.FiliereMapper;
import com.example.filiereservice.repository.FiliereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FiliereService {

    @Autowired
    private FiliereRepository repository;

    @Autowired
    private FiliereMapper mapper;

    // Ajouter une filière
    public FiliereResponseDto ajouter(FiliereRequestDto dto) {
        Filiere filiere = mapper.dtoToEntity(dto);
        repository.save(filiere);
        return mapper.entityToDto(filiere);
    }

    // Obtenir toutes les filières
    public List<FiliereResponseDto> listerToutes() {
        return repository.findAll().stream().map(mapper::entityToDto).toList();
    }

    // Obtenir une filière par son ID
    public FiliereResponseDto obtenirParId(Long id) {
        Filiere filiere = repository.findById(id).orElseThrow();
        return mapper.entityToDto(filiere);
    }

    // Modifier une filière
    public FiliereResponseDto modifier(Long id, FiliereRequestDto dto) {
        Filiere filiere = repository.findById(id).orElseThrow();
        filiere.setCode(dto.getCode());
        filiere.setLibelle(dto.getLibelle());
        repository.save(filiere);
        return mapper.entityToDto(filiere);
    }

    // Supprimer une filière
    public void supprimer(Long id) {
        repository.deleteById(id);
    }
}
