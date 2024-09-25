package com.backend_java.demo.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.backend_java.demo.Utilisateur;
import com.backend_java.demo.UtilisateurToSave;
import com.backend_java.demo.data.UtilisateurEntity;
import com.backend_java.demo.data.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll().stream()
                .map(utilisateur -> new Utilisateur(
                        utilisateur.getNomUtilisateur(),
                        utilisateur.getEmail(),
                        utilisateur.getMotDePasse()
                ))
                .sorted(Comparator.comparing(Utilisateur::nomUtilisateur))
                .collect(Collectors.toList());
    }

    public Utilisateur getByNomUtilisateur(String nomUtilisateur) {
        Optional<UtilisateurEntity> utilisateur = utilisateurRepository.findOneByNomUtilisateurIgnoreCase(nomUtilisateur);
        if (utilisateur.isEmpty()) {
            throw new UtilisateurNonTrouveException(nomUtilisateur);
        }
        return new Utilisateur(
                utilisateur.get().getNomUtilisateur(),
                utilisateur.get().getEmail(),
                utilisateur.get().getMotDePasse()
        );
    }

    public Utilisateur create(UtilisateurToSave utilisateurToSave){
        Optional<UtilisateurEntity> utilisateur = utilisateurRepository.findOneByNomUtilisateurIgnoreCase(utilisateurToSave.nomUtilisateur());
        if (utilisateur.isPresent()) {
            throw new UtilisateurExisteDejaException(utilisateurToSave.nomUtilisateur());
        }

        UtilisateurEntity utilisateurToRegister = new UtilisateurEntity(
                utilisateurToSave.nomUtilisateur(),
                utilisateurToSave.email(),
                utilisateurToSave.motDePasse()
        );

        UtilisateurEntity registeredUtilisateur = utilisateurRepository.save(utilisateurToRegister);

        return getByNomUtilisateur(registeredUtilisateur.getNomUtilisateur());
    }

    public Utilisateur update(UtilisateurToSave utilisateurToSave) {
        Optional<UtilisateurEntity> bonUtilisateur = utilisateurRepository.findOneByNomUtilisateurIgnoreCase(utilisateurToSave.nomUtilisateur());

        if (bonUtilisateur.isEmpty()) {
            throw new UtilisateurNonTrouveException(utilisateurToSave.nomUtilisateur());
        }

        UtilisateurEntity utilisateurAMettreAJour = bonUtilisateur.get();
        utilisateurAMettreAJour.setEmail(utilisateurToSave.email());
        utilisateurAMettreAJour.setMotDePasse(utilisateurToSave.motDePasse());

        UtilisateurEntity updatedUtilisateur = utilisateurRepository.save(utilisateurAMettreAJour);

        return new Utilisateur(
                updatedUtilisateur.getNomUtilisateur(),
                updatedUtilisateur.getEmail(),
                updatedUtilisateur.getMotDePasse()
        );
    }

    public void delete(String nomUtilisateur) {
        Optional<UtilisateurEntity> utilisateur = utilisateurRepository.findOneByNomUtilisateurIgnoreCase(nomUtilisateur);

        if (utilisateur.isEmpty()) {
            throw new UtilisateurNonTrouveException(nomUtilisateur);
        }

        utilisateurRepository.delete(utilisateur.get());
    }

}
