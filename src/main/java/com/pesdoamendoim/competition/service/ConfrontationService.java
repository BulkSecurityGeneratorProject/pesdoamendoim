package com.pesdoamendoim.competition.service;

import com.pesdoamendoim.competition.service.dto.ConfrontationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Confrontation.
 */
public interface ConfrontationService {

    /**
     * Save a confrontation.
     *
     * @param confrontationDTO the entity to save
     * @return the persisted entity
     */
    ConfrontationDTO save(ConfrontationDTO confrontationDTO);

    /**
     * Get all the confrontations.
     *
     * @return the list of entities
     */
    List<ConfrontationDTO> findAll();


    /**
     * Get the "id" confrontation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ConfrontationDTO> findOne(Long id);

    /**
     * Delete the "id" confrontation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
