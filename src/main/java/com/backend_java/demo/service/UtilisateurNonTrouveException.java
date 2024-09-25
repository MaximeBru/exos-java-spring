package com.backend_java.demo.service;

public class UtilisateurNonTrouveException extends RuntimeException{
    public UtilisateurNonTrouveException (String nomUtilisateur){
        super("Utilisateur avec le nom: " + nomUtilisateur + " n'a pas été trouvé");
    }
}
