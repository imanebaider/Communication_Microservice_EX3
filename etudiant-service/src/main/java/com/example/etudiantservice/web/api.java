package com.example.etudiantservice.web;

import com.example.etudiantservice.dto.EtudiantRequestDto;
import com.example.etudiantservice.dto.EtudiantResponseDto;
import com.example.etudiantservice.service.EtudiantService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "Gestion des étudiants",
                description = "Cette API permet de gérer les étudiants (CRUD)",
                version = "1.0.0"
        ),
        servers = @Server(url = "http://localhost:8082")
)
@RestController
@RequestMapping("/api/etudiants")
public class api {

    @Autowired
    private EtudiantService service;

    @Operation(
            summary = "Ajouter un étudiant",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EtudiantRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Étudiant créé",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EtudiantResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "4xx", description = "Erreur client"),
                    @ApiResponse(responseCode = "5xx", description = "Erreur serveur")
            }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<EtudiantResponseDto> addEtudiant(@RequestBody EtudiantRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(
            summary = "Récupérer tous les étudiants",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des étudiants",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = EtudiantResponseDto.class))
                            )
                    ),
                    @ApiResponse(responseCode = "4xx", description = "Erreur client"),
                    @ApiResponse(responseCode = "5xx", description = "Erreur serveur")
            }
    )
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_USER')")
    @GetMapping
    public ResponseEntity<List<EtudiantResponseDto>> getAllEtudiants() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(
            summary = "Récupérer un étudiant par ID",
            parameters = @Parameter(name = "id", description = "ID de l'étudiant", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Étudiant récupéré",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EtudiantResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "4xx", description = "Erreur client"),
                    @ApiResponse(responseCode = "5xx", description = "Erreur serveur")
            }
    )
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<EtudiantResponseDto> getEtudiantById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(
            summary = "Modifier un étudiant",
            parameters = @Parameter(name = "id", description = "ID de l'étudiant", required = true),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EtudiantRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Étudiant modifié",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EtudiantResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "4xx", description = "Erreur client"),
                    @ApiResponse(responseCode = "5xx", description = "Erreur serveur")
            }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EtudiantResponseDto> updateEtudiant(@PathVariable Long id, @RequestBody EtudiantRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(
            summary = "Supprimer un étudiant",
            parameters = @Parameter(name = "id", description = "ID de l'étudiant", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Étudiant supprimé"),
                    @ApiResponse(responseCode = "4xx", description = "Erreur client"),
                    @ApiResponse(responseCode = "5xx", description = "Erreur serveur")
            }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
