package org.vizadev.ddcapp.repository;

import org.vizadev.ddcapp.domain.BriefingHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BriefingHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BriefingHistoryRepository extends JpaRepository<BriefingHistory, Long> {

}
