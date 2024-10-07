
package com.backend_java.demo.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.List;

@Repository
public interface TacheRepository extends JpaRepository<TacheEntity, Long> {

    @NonNull
    List<TacheEntity> findByUtilisateurId(@NonNull Long utilisateurId);

    @NonNull
    List<TacheEntity> findByUtilisateurIdAndStatut(@NonNull Long utilisateurId, @NonNull String statut);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @NonNull
    Optional<TacheEntity> findById(@NonNull Long id);
}
