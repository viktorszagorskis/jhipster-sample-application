package org.vizadev.ddcapp.web.rest;

import org.vizadev.ddcapp.domain.BriefingHistory;
import org.vizadev.ddcapp.service.BriefingHistoryService;
import org.vizadev.ddcapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.vizadev.ddcapp.domain.BriefingHistory}.
 */
@RestController
@RequestMapping("/api")
public class BriefingHistoryResource {

    private final Logger log = LoggerFactory.getLogger(BriefingHistoryResource.class);

    private static final String ENTITY_NAME = "briefingHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BriefingHistoryService briefingHistoryService;

    public BriefingHistoryResource(BriefingHistoryService briefingHistoryService) {
        this.briefingHistoryService = briefingHistoryService;
    }

    /**
     * {@code POST  /briefing-histories} : Create a new briefingHistory.
     *
     * @param briefingHistory the briefingHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new briefingHistory, or with status {@code 400 (Bad Request)} if the briefingHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/briefing-histories")
    public ResponseEntity<BriefingHistory> createBriefingHistory(@RequestBody BriefingHistory briefingHistory) throws URISyntaxException {
        log.debug("REST request to save BriefingHistory : {}", briefingHistory);
        if (briefingHistory.getId() != null) {
            throw new BadRequestAlertException("A new briefingHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BriefingHistory result = briefingHistoryService.save(briefingHistory);
        return ResponseEntity.created(new URI("/api/briefing-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /briefing-histories} : Updates an existing briefingHistory.
     *
     * @param briefingHistory the briefingHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated briefingHistory,
     * or with status {@code 400 (Bad Request)} if the briefingHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the briefingHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/briefing-histories")
    public ResponseEntity<BriefingHistory> updateBriefingHistory(@RequestBody BriefingHistory briefingHistory) throws URISyntaxException {
        log.debug("REST request to update BriefingHistory : {}", briefingHistory);
        if (briefingHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BriefingHistory result = briefingHistoryService.save(briefingHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, briefingHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /briefing-histories} : get all the briefingHistories.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of briefingHistories in body.
     */
    @GetMapping("/briefing-histories")
    public ResponseEntity<List<BriefingHistory>> getAllBriefingHistories(Pageable pageable) {
        log.debug("REST request to get a page of BriefingHistories");
        Page<BriefingHistory> page = briefingHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /briefing-histories/:id} : get the "id" briefingHistory.
     *
     * @param id the id of the briefingHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the briefingHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/briefing-histories/{id}")
    public ResponseEntity<BriefingHistory> getBriefingHistory(@PathVariable Long id) {
        log.debug("REST request to get BriefingHistory : {}", id);
        Optional<BriefingHistory> briefingHistory = briefingHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(briefingHistory);
    }

    /**
     * {@code DELETE  /briefing-histories/:id} : delete the "id" briefingHistory.
     *
     * @param id the id of the briefingHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/briefing-histories/{id}")
    public ResponseEntity<Void> deleteBriefingHistory(@PathVariable Long id) {
        log.debug("REST request to delete BriefingHistory : {}", id);
        briefingHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
