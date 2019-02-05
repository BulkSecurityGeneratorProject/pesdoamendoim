package com.pesdoamendoim.competition.service.mapper;

import com.pesdoamendoim.competition.domain.*;
import com.pesdoamendoim.competition.service.dto.RoundDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Round and its DTO RoundDTO.
 */
@Mapper(componentModel = "spring", uses = {GroupeMapper.class})
public interface RoundMapper extends EntityMapper<RoundDTO, Round> {

    @Mapping(source = "groupe.id", target = "groupeId")
    @Mapping(source = "groupe.groupName", target = "groupeGroupName")
    RoundDTO toDto(Round round);

    @Mapping(source = "groupeId", target = "groupe")
    Round toEntity(RoundDTO roundDTO);

    default Round fromId(Long id) {
        if (id == null) {
            return null;
        }
        Round round = new Round();
        round.setId(id);
        return round;
    }
}
