package com.pesdoamendoim.competition.web.rest;
import com.pesdoamendoim.competition.service.RoundService;
import com.pesdoamendoim.competition.web.rest.errors.BadRequestAlertException;
import com.pesdoamendoim.competition.web.rest.util.HeaderUtil;
import com.pesdoamendoim.competition.web.rest.util.PaginationUtil;
import com.pesdoamendoim.competition.service.dto.RoundDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Round.
 */
@RestController
@RequestMapping("/api")
public class RoundResource {

    private final Logger log = LoggerFactory.getLogger(RoundResource.class);

    private static final String ENTITY_NAME = "round";

    private final RoundService roundService;

    public RoundResource(RoundService roundService) {
        this.roundService = roundService;
    }

    /**
     * POST  /rounds : Create a new round.
     *
     * @param roundDTO the roundDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roundDTO, or with status 400 (Bad Request) if the round has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rounds")
    public ResponseEntity<RoundDTO> createRound(@Valid @RequestBody RoundDTO roundDTO) throws URISyntaxException {
        log.debug("REST request to save Round : {}", roundDTO);
        if (roundDTO.getId() != null) {
            throw new BadRequestAlertException("A new round cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoundDTO result = roundService.save(roundDTO);
        return ResponseEntity.created(new URI("/api/rounds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rounds : Updates an existing round.
     *
     * @param roundDTO the roundDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roundDTO,
     * or with status 400 (Bad Request) if the roundDTO is not valid,
     * or with status 500 (Internal Server Error) if the roundDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rounds")
    public ResponseEntity<RoundDTO> updateRound(@Valid @RequestBody RoundDTO roundDTO) throws URISyntaxException {
        log.debug("REST request to update Round : {}", roundDTO);
        if (roundDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RoundDTO result = roundService.save(roundDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roundDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rounds : get all the rounds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rounds in body
     */
    @GetMapping("/rounds")
    public ResponseEntity<List<RoundDTO>> getAllRounds(Pageable pageable) {
        log.debug("REST request to get a page of Rounds");
        Page<RoundDTO> page = roundService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rounds");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /rounds/:id : get the "id" round.
     *
     * @param id the id of the roundDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roundDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rounds/{id}")
    public ResponseEntity<RoundDTO> getRound(@PathVariable Long id) {
        log.debug("REST request to get Round : {}", id);
        Optional<RoundDTO> roundDTO = roundService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roundDTO);
    }

    /**
     * DELETE  /rounds/:id : delete the "id" round.
     *
     * @param id the id of the roundDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rounds/{id}")
    public ResponseEntity<Void> deleteRound(@PathVariable Long id) {
        log.debug("REST request to delete Round : {}", id);
        roundService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
