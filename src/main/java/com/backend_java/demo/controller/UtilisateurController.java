package com.backend_java.demo.controller;

import com.backend_java.demo.Utilisateur;
import com.backend_java.demo.UtilisateurToSave;
import com.backend_java.demo.service.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Utilisateur API")
@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {

    @Autowired
    UtilisateurService  utilisateurService;

    @Operation(summary = "Trouver utilisateurs", description = "Trouver utilisateurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateurs list",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Utilisateur.class)))})
    })
    @GetMapping
    public List<Utilisateur> list() {
        return utilisateurService.getAllUtilisateurs();
    }

    @Operation(summary = "Trouver un utilisateur", description = "Trouver un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Utilisateur.class))}),
            @ApiResponse(responseCode = "404", description = "Utilisateur avec le nom spécifié non trouvé.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class))})

    })
    @GetMapping("{nomUtilisateur}")
    public Utilisateur getByNomUtilisateur(@PathVariable("nomUtilisateur") String nomUtilisateur) {
        return utilisateurService.getByNomUtilisateur(nomUtilisateur);
    }

    @Operation(summary = "Créer un utilisateur", description = "Créer un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur créé",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UtilisateurToSave.class))}),
            @ApiResponse(responseCode = "400", description = "Utilisateur avec le nom spécifié non trouvé.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class))})

    })
    @PostMapping
    public Utilisateur createUtilisateur(@RequestBody @Valid UtilisateurToSave utilisateurToSave) {
        return utilisateurService.create(utilisateurToSave);
    }

    @Operation(summary = "Supprimer un utilisateur", description = "Supprimer un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'utilisateur à été supprimé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur with avec le nom spécifié non trouvé.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class))})

    })
    @DeleteMapping("{nomUtilisateur}")
    public void deleteUtilisateurParNom(@PathVariable("nomUtilisateur") String nomUtilisateur) {
        utilisateurService.delete(nomUtilisateur);
    }
}
