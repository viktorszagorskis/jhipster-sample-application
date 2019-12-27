package org.vizadev.ddcapp.repository;

import org.vizadev.ddcapp.domain.PersistentToken;
import org.vizadev.ddcapp.domain.User;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the {@link PersistentToken} entity.
 */
public interface PersistentTokenRepository extends JpaRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);

}
