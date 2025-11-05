package com.example.etudiantservice.service;

import com.example.etudiantservice.dto.EtudiantRequestDto;
import com.example.etudiantservice.dto.EtudiantResponseDto;
import com.example.etudiantservice.dto.FiliereResponseDto;
import com.example.etudiantservice.entities.Etudiant;

import com.example.etudiantservice.mapper.EtudiantMapper;
import com.example.etudiantservice.repository.EtudiantRepository;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtudiantService {
    @Autowired
    private EtudiantRepository repository;

    @Autowired
    private EtudiantMapper mapper;

    private final RestTemplate restTemplate = new RestTemplate();

    public EtudiantResponseDto create(EtudiantRequestDto dto) {
        Etudiant etudiant = mapper.dtoToEntity(dto);
        repository.save(etudiant);
        FiliereResponseDto filiere = getFiliere(etudiant.getIdFiliere());
        return mapper.entityToDto(etudiant, filiere);
    }

    public List<EtudiantResponseDto> getAll() {
        return repository.findAll().stream()
                .map(e -> mapper.entityToDto(e, getFiliere(e.getIdFiliere())))
                .toList();
    }

    public EtudiantResponseDto getById(Long id) {
        Etudiant etudiant = repository.findById(id).orElseThrow();
        FiliereResponseDto filiere = getFiliere(etudiant.getIdFiliere());
        return mapper.entityToDto(etudiant, filiere);
    }

    private FiliereResponseDto getFiliere(Long idFiliere) {
        return restTemplate.getForObject("http://localhost:8083/api/filieres/" + idFiliere,
                FiliereResponseDto.class);
    }
    public EtudiantResponseDto update(Long id, EtudiantRequestDto dto) {
        Etudiant etudiant = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
        etudiant.setNom(dto.getNom());
        etudiant.setPrenom(dto.getPrenom());
        etudiant.setCne(dto.getCne());
        etudiant.setIdFiliere(dto.getIdFiliere());
        repository.save(etudiant);
        FiliereResponseDto filiere = getFiliere(etudiant.getIdFiliere());
        return mapper.entityToDto(etudiant, filiere);
    }



    public void delete(Long id) {
        Etudiant etudiant = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
        repository.delete(etudiant);
    }

}