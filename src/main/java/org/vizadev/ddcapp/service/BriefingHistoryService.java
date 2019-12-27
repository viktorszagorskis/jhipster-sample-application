package org.vizadev.ddcapp.service;

import org.vizadev.ddcapp.domain.BriefingHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link BriefingHistory}.
 */
public interface BriefingHistoryService {

    /**
     * Save a briefingHistory.
     *
     * @param briefingHistory the entity to save.
     * @return the persisted entity.
     */
    BriefingHistory save(BriefingHistory briefingHistory);

    /**
     * Get all the briefingHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BriefingHistory> findAll(Pageable pageable);


    /**
     * Get the "id" briefingHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BriefingHistory> findOne(Long id);

    /**
     * Delete the "id" briefingHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
