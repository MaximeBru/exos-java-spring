
package com.backend_java.demo.controller;

import com.backend_java.demo.records.TacheToSave;
import com.backend_java.demo.records.TacheToEdit;
import com.backend_java.demo.service.TacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tache API")
@RestController
@RequestMapping("/taches")
public class TacheController {

    @Autowired
    private TacheService tacheService;

    @Operation(summary = "Trouver toutes les tâches d'un utilisateur", description = "Trouver toutes les tâches par ID utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des tâches",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TacheToSave.class)))}
            ),
            @ApiResponse(responseCode = "404", description = "Tâche non trouvée")
    })
    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<TacheToSave>> getTachesByUtilisateur(@PathVariable Long utilisateurId) {
        List<TacheToSave> taches = tacheService.findTachesByUtilisateurId(utilisateurId);
        return ResponseEntity.ok(taches);
    }

    @Operation(summary = "Créer une nouvelle tâche", description = "Créer une tâche")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tâche créée",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TacheToSave.class))}
            )
    })
    @PostMapping
    public ResponseEntity<TacheToSave> createTache(@RequestBody @Valid TacheToSave tacheToSave) {
        TacheToSave savedTache = tacheService.saveTache(tacheToSave);
        return ResponseEntity.ok(savedTache);
    }

    @Operation(summary = "Mettre à jour une tâche", description = "Mettre à jour une tâche existante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tâche mise à jour",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TacheToSave.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Tâche non trouvée")
    })
    @PutMapping("/{tacheId}")
    public ResponseEntity<TacheToSave> updateTache(@PathVariable Long tacheId, @RequestBody @Valid TacheToEdit tacheToEdit) {
        TacheToSave updatedTache = tacheService.updateTache(tacheId, tacheToEdit);
        return ResponseEntity.ok(updatedTache);
    }

    @Operation(summary = "Supprimer une tâche", description = "Supprimer une tâche")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tâche supprimée"),
            @ApiResponse(responseCode = "404", description = "Tâche non trouvée")
    })
    @DeleteMapping("/{tacheId}")
    public ResponseEntity<Void> deleteTache(@PathVariable Long tacheId) {
        tacheService.deleteTache(tacheId);
        return ResponseEntity.noContent().build();
    }
}
