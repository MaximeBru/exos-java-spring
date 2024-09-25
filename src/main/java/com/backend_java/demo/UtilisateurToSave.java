package com.backend_java.demo;

import jakarta.validation.constraints.NotBlank;

public record UtilisateurToSave(
        @NotBlank(message = "nom est obligatoire") String nomUtilisateur,
        @NotBlank(message = "Email est obligatoire") String email,
        @NotBlank(message = "Mot de passe est obligatoire") String motDePasse
) {
}
