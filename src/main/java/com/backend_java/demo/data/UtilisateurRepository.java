package com.backend_java.demo.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity, Long> {
    Optional<UtilisateurEntity> findOneByNomUtilisateurIgnoreCase(String nomUtilisateur);
    Optional<UtilisateurEntity> findOneWithRolesByLoginIgnoreCase(String login);
}



