package com.pesdoamendoim.competition.service.impl;

import com.pesdoamendoim.competition.service.GroupeService;
import com.pesdoamendoim.competition.domain.Groupe;
import com.pesdoamendoim.competition.repository.GroupeRepository;
import com.pesdoamendoim.competition.service.dto.GroupeDTO;
import com.pesdoamendoim.competition.service.mapper.GroupeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Groupe.
 */
@Service
@Transactional
public class GroupeServiceImpl implements GroupeService {

    private final Logger log = LoggerFactory.getLogger(GroupeServiceImpl.class);

    private final GroupeRepository groupeRepository;

    private final GroupeMapper groupeMapper;

    public GroupeServiceImpl(GroupeRepository groupeRepository, GroupeMapper groupeMapper) {
        this.groupeRepository = groupeRepository;
        this.groupeMapper = groupeMapper;
    }

    /**
     * Save a groupe.
     *
     * @param groupeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GroupeDTO save(GroupeDTO groupeDTO) {
        log.debug("Request to save Groupe : {}", groupeDTO);
        Groupe groupe = groupeMapper.toEntity(groupeDTO);
        groupe = groupeRepository.save(groupe);
        return groupeMapper.toDto(groupe);
    }

    /**
     * Get all the groupes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GroupeDTO> findAll() {
        log.debug("Request to get all Groupes");
        return groupeRepository.findAll().stream()
            .map(groupeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one groupe by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GroupeDTO> findOne(Long id) {
        log.debug("Request to get Groupe : {}", id);
        return groupeRepository.findById(id)
            .map(groupeMapper::toDto);
    }

    /**
     * Delete the groupe by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Groupe : {}", id);        groupeRepository.deleteById(id);
    }
}
