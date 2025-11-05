package com.example.filiereservice.web;

import com.example.filiereservice.dto.FiliereRequestDto;
import com.example.filiereservice.dto.FiliereResponseDto;
import com.example.filiereservice.service.FiliereService;
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
                title = "Gestion des filières",
                description = "Cette API permet de gérer les filières (CRUD)",
                version = "1.0.0"
        ),
        servers = @Server(url = "http://localhost:8081")
)
@RestController
@RequestMapping("/api/filieres")
public class Api {

    @Autowired
    private FiliereService service;

    @Operation(
            summary = "Ajouter une filière",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FiliereRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filière créée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FiliereResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "4xx", description = "Erreur client"),
                    @ApiResponse(responseCode = "5xx", description = "Erreur serveur")
            }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<FiliereResponseDto> addFiliere(@RequestBody FiliereRequestDto dto) {
        return ResponseEntity.ok(service.ajouter(dto));
    }

    @Operation(
            summary = "Lister toutes les filières",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des filières",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = FiliereResponseDto.class))
                            )
                    ),
                    @ApiResponse(responseCode = "4xx", description = "Erreur client"),
                    @ApiResponse(responseCode = "5xx", description = "Erreur serveur")
            }
    )
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_USER')")
    @GetMapping
    public ResponseEntity<List<FiliereResponseDto>> getAllFilieres() {
        return ResponseEntity.ok(service.listerToutes());
    }

    @Operation(
            summary = "Obtenir une filière par ID",
            parameters = @Parameter(name = "id", description = "ID de la filière", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filière récupérée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FiliereResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "4xx", description = "Erreur client"),
                    @ApiResponse(responseCode = "5xx", description = "Erreur serveur")
            }
    )
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN','SCOPE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<FiliereResponseDto> getFiliereById(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenirParId(id));
    }

    @Operation(
            summary = "Modifier une filière",
            parameters = @Parameter(name = "id", description = "ID de la filière", required = true),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FiliereRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filière modifiée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FiliereResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "4xx", description = "Erreur client"),
                    @ApiResponse(responseCode = "5xx", description = "Erreur serveur")
            }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<FiliereResponseDto> updateFiliere(@PathVariable Long id, @RequestBody FiliereRequestDto dto) {
        return ResponseEntity.ok(service.modifier(id, dto));
    }

    @Operation(
            summary = "Supprimer une filière",
            parameters = @Parameter(name = "id", description = "ID de la filière", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filière supprimée"),
                    @ApiResponse(responseCode = "4xx", description = "Erreur client"),
                    @ApiResponse(responseCode = "5xx", description = "Erreur serveur")
            }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFiliere(@PathVariable Long id) {
        service.supprimer(id);
        return ResponseEntity.ok().build();
    }
}
