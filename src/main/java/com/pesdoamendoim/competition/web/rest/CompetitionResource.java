package com.pesdoamendoim.competition.web.rest;
import com.pesdoamendoim.competition.service.CompetitionService;
import com.pesdoamendoim.competition.web.rest.errors.BadRequestAlertException;
import com.pesdoamendoim.competition.web.rest.util.HeaderUtil;
import com.pesdoamendoim.competition.service.dto.CompetitionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Competition.
 */
@RestController
@RequestMapping("/api")
public class CompetitionResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionResource.class);

    private static final String ENTITY_NAME = "competition";

    private final CompetitionService competitionService;

    public CompetitionResource(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    /**
     * POST  /competitions : Create a new competition.
     *
     * @param competitionDTO the competitionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new competitionDTO, or with status 400 (Bad Request) if the competition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/competitions")
    public ResponseEntity<CompetitionDTO> createCompetition(@Valid @RequestBody CompetitionDTO competitionDTO) throws URISyntaxException {
        log.debug("REST request to save Competition : {}", competitionDTO);
        if (competitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new competition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitionDTO result = competitionService.save(competitionDTO);
        return ResponseEntity.created(new URI("/api/competitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /competitions : Updates an existing competition.
     *
     * @param competitionDTO the competitionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated competitionDTO,
     * or with status 400 (Bad Request) if the competitionDTO is not valid,
     * or with status 500 (Internal Server Error) if the competitionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/competitions")
    public ResponseEntity<CompetitionDTO> updateCompetition(@Valid @RequestBody CompetitionDTO competitionDTO) throws URISyntaxException {
        log.debug("REST request to update Competition : {}", competitionDTO);
        if (competitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompetitionDTO result = competitionService.save(competitionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, competitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /competitions : get all the competitions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of competitions in body
     */
    @GetMapping("/competitions")
    public List<CompetitionDTO> getAllCompetitions() {
        log.debug("REST request to get all Competitions");
        return competitionService.findAll();
    }

    /**
     * GET  /competitions/:id : get the "id" competition.
     *
     * @param id the id of the competitionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the competitionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/competitions/{id}")
    public ResponseEntity<CompetitionDTO> getCompetition(@PathVariable Long id) {
        log.debug("REST request to get Competition : {}", id);
        Optional<CompetitionDTO> competitionDTO = competitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competitionDTO);
    }

    /**
     * DELETE  /competitions/:id : delete the "id" competition.
     *
     * @param id the id of the competitionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/competitions/{id}")
    public ResponseEntity<Void> deleteCompetition(@PathVariable Long id) {
        log.debug("REST request to delete Competition : {}", id);
        competitionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
