package com.pesdoamendoim.competition.service.mapper;

import com.pesdoamendoim.competition.domain.*;
import com.pesdoamendoim.competition.service.dto.GroupeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Groupe and its DTO GroupeDTO.
 */
@Mapper(componentModel = "spring", uses = {CompetitionMapper.class})
public interface GroupeMapper extends EntityMapper<GroupeDTO, Groupe> {

    @Mapping(source = "competition.id", target = "competitionId")
    @Mapping(source = "competition.competitionName", target = "competitionCompetitionName")
    GroupeDTO toDto(Groupe groupe);

    @Mapping(source = "competitionId", target = "competition")
    Groupe toEntity(GroupeDTO groupeDTO);

    default Groupe fromId(Long id) {
        if (id == null) {
            return null;
        }
        Groupe groupe = new Groupe();
        groupe.setId(id);
        return groupe;
    }
}
