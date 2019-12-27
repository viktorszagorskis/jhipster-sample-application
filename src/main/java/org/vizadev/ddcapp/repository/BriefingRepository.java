package org.vizadev.ddcapp.repository;

import org.vizadev.ddcapp.domain.Briefing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Briefing entity.
 */
@Repository
public interface BriefingRepository extends JpaRepository<Briefing, Long> {

    @Query(value = "select distinct briefing from Briefing briefing left join fetch briefing.tasks",
        countQuery = "select count(distinct briefing) from Briefing briefing")
    Page<Briefing> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct briefing from Briefing briefing left join fetch briefing.tasks")
    List<Briefing> findAllWithEagerRelationships();

    @Query("select briefing from Briefing briefing left join fetch briefing.tasks where briefing.id =:id")
    Optional<Briefing> findOneWithEagerRelationships(@Param("id") Long id);

}
