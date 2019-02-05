package com.pesdoamendoim.competition.service;

import com.pesdoamendoim.competition.service.dto.RoundDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Round.
 */
public interface RoundService {

    /**
     * Save a round.
     *
     * @param roundDTO the entity to save
     * @return the persisted entity
     */
    RoundDTO save(RoundDTO roundDTO);

    /**
     * Get all the rounds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RoundDTO> findAll(Pageable pageable);


    /**
     * Get the "id" round.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RoundDTO> findOne(Long id);

    /**
     * Delete the "id" round.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
