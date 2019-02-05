package com.pesdoamendoim.competition.service.impl;

import com.pesdoamendoim.competition.service.ConfrontationService;
import com.pesdoamendoim.competition.domain.Confrontation;
import com.pesdoamendoim.competition.repository.ConfrontationRepository;
import com.pesdoamendoim.competition.service.dto.ConfrontationDTO;
import com.pesdoamendoim.competition.service.mapper.ConfrontationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Confrontation.
 */
@Service
@Transactional
public class ConfrontationServiceImpl implements ConfrontationService {

    private final Logger log = LoggerFactory.getLogger(ConfrontationServiceImpl.class);

    private final ConfrontationRepository confrontationRepository;

    private final ConfrontationMapper confrontationMapper;

    public ConfrontationServiceImpl(ConfrontationRepository confrontationRepository, ConfrontationMapper confrontationMapper) {
        this.confrontationRepository = confrontationRepository;
        this.confrontationMapper = confrontationMapper;
    }

    /**
     * Save a confrontation.
     *
     * @param confrontationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConfrontationDTO save(ConfrontationDTO confrontationDTO) {
        log.debug("Request to save Confrontation : {}", confrontationDTO);
        Confrontation confrontation = confrontationMapper.toEntity(confrontationDTO);
        confrontation = confrontationRepository.save(confrontation);
        return confrontationMapper.toDto(confrontation);
    }

    /**
     * Get all the confrontations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConfrontationDTO> findAll() {
        log.debug("Request to get all Confrontations");
        return confrontationRepository.findAll().stream()
            .map(confrontationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one confrontation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConfrontationDTO> findOne(Long id) {
        log.debug("Request to get Confrontation : {}", id);
        return confrontationRepository.findById(id)
            .map(confrontationMapper::toDto);
    }

    /**
     * Delete the confrontation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Confrontation : {}", id);        confrontationRepository.deleteById(id);
    }
}
