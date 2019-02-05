package com.pesdoamendoim.competition.service.impl;

import com.pesdoamendoim.competition.service.RoundService;
import com.pesdoamendoim.competition.domain.Round;
import com.pesdoamendoim.competition.repository.RoundRepository;
import com.pesdoamendoim.competition.service.dto.RoundDTO;
import com.pesdoamendoim.competition.service.mapper.RoundMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Round.
 */
@Service
@Transactional
public class RoundServiceImpl implements RoundService {

    private final Logger log = LoggerFactory.getLogger(RoundServiceImpl.class);

    private final RoundRepository roundRepository;

    private final RoundMapper roundMapper;

    public RoundServiceImpl(RoundRepository roundRepository, RoundMapper roundMapper) {
        this.roundRepository = roundRepository;
        this.roundMapper = roundMapper;
    }

    /**
     * Save a round.
     *
     * @param roundDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RoundDTO save(RoundDTO roundDTO) {
        log.debug("Request to save Round : {}", roundDTO);
        Round round = roundMapper.toEntity(roundDTO);
        round = roundRepository.save(round);
        return roundMapper.toDto(round);
    }

    /**
     * Get all the rounds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RoundDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rounds");
        return roundRepository.findAll(pageable)
            .map(roundMapper::toDto);
    }


    /**
     * Get one round by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RoundDTO> findOne(Long id) {
        log.debug("Request to get Round : {}", id);
        return roundRepository.findById(id)
            .map(roundMapper::toDto);
    }

    /**
     * Delete the round by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Round : {}", id);        roundRepository.deleteById(id);
    }
}
