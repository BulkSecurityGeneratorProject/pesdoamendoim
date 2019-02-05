package com.pesdoamendoim.competition.service.mapper;

import com.pesdoamendoim.competition.domain.*;
import com.pesdoamendoim.competition.service.dto.ConfrontationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Confrontation and its DTO ConfrontationDTO.
 */
@Mapper(componentModel = "spring", uses = {PlayerMapper.class, RoundMapper.class})
public interface ConfrontationMapper extends EntityMapper<ConfrontationDTO, Confrontation> {

    @Mapping(source = "player1.id", target = "player1Id")
    @Mapping(source = "player1.playerName", target = "player1PlayerName")
    @Mapping(source = "player2.id", target = "player2Id")
    @Mapping(source = "player2.playerName", target = "player2PlayerName")
    @Mapping(source = "round.id", target = "roundId")
    @Mapping(source = "round.roundName", target = "roundRoundName")
    ConfrontationDTO toDto(Confrontation confrontation);

    @Mapping(source = "player1Id", target = "player1")
    @Mapping(source = "player2Id", target = "player2")
    @Mapping(source = "roundId", target = "round")
    Confrontation toEntity(ConfrontationDTO confrontationDTO);

    default Confrontation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Confrontation confrontation = new Confrontation();
        confrontation.setId(id);
        return confrontation;
    }
}
