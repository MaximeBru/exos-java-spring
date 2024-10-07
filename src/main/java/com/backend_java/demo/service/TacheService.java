
package com.backend_java.demo.service;

import com.backend_java.demo.data.TacheEntity;
import com.backend_java.demo.data.TacheRepository;
import com.backend_java.demo.records.TacheToSave;
import com.backend_java.demo.records.TacheToEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TacheService {

    @Autowired
    private TacheRepository tacheRepository;

    // Retrieve tasks by user ID
    public List<TacheToSave> findTachesByUtilisateurId(Long utilisateurId) {
        return tacheRepository.findByUtilisateurId(utilisateurId).stream()
                .map(tache -> new TacheToSave(
                        tache.getTitre(),
                        tache.getDescription(),
                        tache.getDateEcheance(),
                        tache.getStatut()
                ))
                .collect(Collectors.toList());
    }

    // Retrieve tasks by user ID and status
    public List<TacheToSave> findTachesByUtilisateurIdAndStatut(Long utilisateurId, String statut) {
        return tacheRepository.findByUtilisateurIdAndStatut(utilisateurId, statut).stream()
                .map(tache -> new TacheToSave(
                        tache.getTitre(),
                        tache.getDescription(),
                        tache.getDateEcheance(),
                        tache.getStatut()
                ))
                .collect(Collectors.toList());
    }

    // Save a new task with DTO
    @Transactional
    public TacheToSave saveTache(TacheToSave tacheToSave) {
        TacheEntity tacheEntity = new TacheEntity(
                tacheToSave.titre(),
                tacheToSave.description(),
                tacheToSave.dateEcheance(),
                tacheToSave.statut()
        );
        TacheEntity savedTache = tacheRepository.save(tacheEntity);
        return new TacheToSave(savedTache.getTitre(), savedTache.getDescription(), savedTache.getDateEcheance(), savedTache.getStatut());
    }

    // Update a task
    @Transactional
    public TacheToSave updateTache(Long tacheId, TacheToEdit tacheToEdit) {
        Optional<TacheEntity> tacheOptional = tacheRepository.findById(tacheId);
        if (tacheOptional.isEmpty()) {
            throw new TacheNonTrouveeException(tacheId);
        }

        TacheEntity tacheEntity = tacheOptional.get();
        tacheEntity.setTitre(tacheToEdit.titre());
        tacheEntity.setDescription(tacheToEdit.description());
        tacheEntity.setDateEcheance(tacheToEdit.dateEcheance());
        tacheEntity.setStatut(tacheToEdit.statut());

        TacheEntity updatedTache = tacheRepository.save(tacheEntity);
        return new TacheToSave(updatedTache.getTitre(), updatedTache.getDescription(), updatedTache.getDateEcheance(), updatedTache.getStatut());
    }

    // Delete a task
    @Transactional
    public void deleteTache(Long tacheId) {
        Optional<TacheEntity> tacheOptional = tacheRepository.findById(tacheId);
        if (tacheOptional.isEmpty()) {
            throw new TacheNonTrouveeException(tacheId);
        }
        tacheRepository.deleteById(tacheId);
    }
}
