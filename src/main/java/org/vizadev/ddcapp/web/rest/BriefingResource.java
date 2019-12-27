package org.vizadev.ddcapp.web.rest;

import org.vizadev.ddcapp.domain.Briefing;
import org.vizadev.ddcapp.repository.BriefingRepository;
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
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.vizadev.ddcapp.domain.Briefing}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BriefingResource {

    private final Logger log = LoggerFactory.getLogger(BriefingResource.class);

    private static final String ENTITY_NAME = "briefing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BriefingRepository briefingRepository;

    public BriefingResource(BriefingRepository briefingRepository) {
        this.briefingRepository = briefingRepository;
    }

    /**
     * {@code POST  /briefings} : Create a new briefing.
     *
     * @param briefing the briefing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new briefing, or with status {@code 400 (Bad Request)} if the briefing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/briefings")
    public ResponseEntity<Briefing> createBriefing(@RequestBody Briefing briefing) throws URISyntaxException {
        log.debug("REST request to save Briefing : {}", briefing);
        if (briefing.getId() != null) {
            throw new BadRequestAlertException("A new briefing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Briefing result = briefingRepository.save(briefing);
        return ResponseEntity.created(new URI("/api/briefings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /briefings} : Updates an existing briefing.
     *
     * @param briefing the briefing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated briefing,
     * or with status {@code 400 (Bad Request)} if the briefing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the briefing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/briefings")
    public ResponseEntity<Briefing> updateBriefing(@RequestBody Briefing briefing) throws URISyntaxException {
        log.debug("REST request to update Briefing : {}", briefing);
        if (briefing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Briefing result = briefingRepository.save(briefing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, briefing.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /briefings} : get all the briefings.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of briefings in body.
     */
    @GetMapping("/briefings")
    public ResponseEntity<List<Briefing>> getAllBriefings(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Briefings");
        Page<Briefing> page;
        if (eagerload) {
            page = briefingRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = briefingRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /briefings/:id} : get the "id" briefing.
     *
     * @param id the id of the briefing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the briefing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/briefings/{id}")
    public ResponseEntity<Briefing> getBriefing(@PathVariable Long id) {
        log.debug("REST request to get Briefing : {}", id);
        Optional<Briefing> briefing = briefingRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(briefing);
    }

    /**
     * {@code DELETE  /briefings/:id} : delete the "id" briefing.
     *
     * @param id the id of the briefing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/briefings/{id}")
    public ResponseEntity<Void> deleteBriefing(@PathVariable Long id) {
        log.debug("REST request to delete Briefing : {}", id);
        briefingRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
