package com.backend_java.demo;

import jakarta.validation.constraints.NotBlank;

public record UtilisateurCredentials(
        @NotBlank(message = "Login est obligatoire") String login,
        @NotBlank(message = "Mot de passe est obligatoire") String password

){}
