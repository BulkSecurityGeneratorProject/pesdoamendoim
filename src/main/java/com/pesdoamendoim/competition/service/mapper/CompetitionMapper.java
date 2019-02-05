package com.pesdoamendoim.competition.service.mapper;

import com.pesdoamendoim.competition.domain.*;
import com.pesdoamendoim.competition.service.dto.CompetitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Competition and its DTO CompetitionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompetitionMapper extends EntityMapper<CompetitionDTO, Competition> {



    default Competition fromId(Long id) {
        if (id == null) {
            return null;
        }
        Competition competition = new Competition();
        competition.setId(id);
        return competition;
    }
}
