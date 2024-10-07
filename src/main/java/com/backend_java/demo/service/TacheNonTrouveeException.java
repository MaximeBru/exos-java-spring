
package com.backend_java.demo.service;

public class TacheNonTrouveeException extends RuntimeException {
    public TacheNonTrouveeException(Long id) {
        super("Tâche avec l'ID " + id + " non trouvée.");
    }
}
