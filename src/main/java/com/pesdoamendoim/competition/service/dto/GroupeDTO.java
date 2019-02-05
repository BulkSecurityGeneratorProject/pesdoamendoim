package com.pesdoamendoim.competition.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Groupe entity.
 */
public class GroupeDTO implements Serializable {

    private Long id;

    @NotNull
    private String groupName;


    private Long competitionId;

    private String competitionCompetitionName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public String getCompetitionCompetitionName() {
        return competitionCompetitionName;
    }

    public void setCompetitionCompetitionName(String competitionCompetitionName) {
        this.competitionCompetitionName = competitionCompetitionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GroupeDTO groupeDTO = (GroupeDTO) o;
        if (groupeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), groupeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GroupeDTO{" +
            "id=" + getId() +
            ", groupName='" + getGroupName() + "'" +
            ", competition=" + getCompetitionId() +
            ", competition='" + getCompetitionCompetitionName() + "'" +
            "}";
    }
}
