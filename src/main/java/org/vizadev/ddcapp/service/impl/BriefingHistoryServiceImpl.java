package org.vizadev.ddcapp.service.impl;

import org.vizadev.ddcapp.service.BriefingHistoryService;
import org.vizadev.ddcapp.domain.BriefingHistory;
import org.vizadev.ddcapp.repository.BriefingHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BriefingHistory}.
 */
@Service
@Transactional
public class BriefingHistoryServiceImpl implements BriefingHistoryService {

    private final Logger log = LoggerFactory.getLogger(BriefingHistoryServiceImpl.class);

    private final BriefingHistoryRepository briefingHistoryRepository;

    public BriefingHistoryServiceImpl(BriefingHistoryRepository briefingHistoryRepository) {
        this.briefingHistoryRepository = briefingHistoryRepository;
    }

    /**
     * Save a briefingHistory.
     *
     * @param briefingHistory the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BriefingHistory save(BriefingHistory briefingHistory) {
        log.debug("Request to save BriefingHistory : {}", briefingHistory);
        return briefingHistoryRepository.save(briefingHistory);
    }

    /**
     * Get all the briefingHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BriefingHistory> findAll(Pageable pageable) {
        log.debug("Request to get all BriefingHistories");
        return briefingHistoryRepository.findAll(pageable);
    }


    /**
     * Get one briefingHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BriefingHistory> findOne(Long id) {
        log.debug("Request to get BriefingHistory : {}", id);
        return briefingHistoryRepository.findById(id);
    }

    /**
     * Delete the briefingHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BriefingHistory : {}", id);
        briefingHistoryRepository.deleteById(id);
    }
}
