package com.pesdoamendoim.competition.service.impl;

import com.pesdoamendoim.competition.service.CompetitionService;
import com.pesdoamendoim.competition.domain.Competition;
import com.pesdoamendoim.competition.repository.CompetitionRepository;
import com.pesdoamendoim.competition.service.dto.CompetitionDTO;
import com.pesdoamendoim.competition.service.mapper.CompetitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Competition.
 */
@Service
@Transactional
public class CompetitionServiceImpl implements CompetitionService {

    private final Logger log = LoggerFactory.getLogger(CompetitionServiceImpl.class);

    private final CompetitionRepository competitionRepository;

    private final CompetitionMapper competitionMapper;

    public CompetitionServiceImpl(CompetitionRepository competitionRepository, CompetitionMapper competitionMapper) {
        this.competitionRepository = competitionRepository;
        this.competitionMapper = competitionMapper;
    }

    /**
     * Save a competition.
     *
     * @param competitionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompetitionDTO save(CompetitionDTO competitionDTO) {
        log.debug("Request to save Competition : {}", competitionDTO);
        Competition competition = competitionMapper.toEntity(competitionDTO);
        competition = competitionRepository.save(competition);
        return competitionMapper.toDto(competition);
    }

    /**
     * Get all the competitions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CompetitionDTO> findAll() {
        log.debug("Request to get all Competitions");
        return competitionRepository.findAll().stream()
            .map(competitionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one competition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompetitionDTO> findOne(Long id) {
        log.debug("Request to get Competition : {}", id);
        return competitionRepository.findById(id)
            .map(competitionMapper::toDto);
    }

    /**
     * Delete the competition by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Competition : {}", id);        competitionRepository.deleteById(id);
    }
}
