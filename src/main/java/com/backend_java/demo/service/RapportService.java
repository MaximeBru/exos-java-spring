

package com.backend_java.demo.service;

import com.backend_java.demo.data.TacheEntity;
import com.backend_java.demo.data.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RapportService {

    @Autowired
    private TacheRepository tacheRepository;

    @Async
    public void genererRapport(Long utilisateurId) {
        List<TacheEntity> taches = tacheRepository.findByUtilisateurId(utilisateurId);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Logique pour générer un rapport, par exemple en le sauvegardant dans un fichier ou une base de données
        System.out.println("Rapport généré pour l'utilisateur " + utilisateurId + " avec " + taches.size() + " tâches.");
    }
}
