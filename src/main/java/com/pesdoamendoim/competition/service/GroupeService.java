package com.pesdoamendoim.competition.service;

import com.pesdoamendoim.competition.service.dto.GroupeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Groupe.
 */
public interface GroupeService {

    /**
     * Save a groupe.
     *
     * @param groupeDTO the entity to save
     * @return the persisted entity
     */
    GroupeDTO save(GroupeDTO groupeDTO);

    /**
     * Get all the groupes.
     *
     * @return the list of entities
     */
    List<GroupeDTO> findAll();


    /**
     * Get the "id" groupe.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GroupeDTO> findOne(Long id);

    /**
     * Delete the "id" groupe.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
