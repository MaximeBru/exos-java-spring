package com.backend_java.demo.service;

public class UtilisateurExisteDejaException extends RuntimeException {
    public UtilisateurExisteDejaException(String nomUtilisateur){
        super("Utilisateur " + nomUtilisateur + "existe deja");
    }
}
