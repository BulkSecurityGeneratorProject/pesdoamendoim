package com.pesdoamendoim.competition.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Competition entity.
 */
public class CompetitionDTO implements Serializable {

    private Long id;

    @NotNull
    private String competitionName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompetitionDTO competitionDTO = (CompetitionDTO) o;
        if (competitionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competitionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompetitionDTO{" +
            "id=" + getId() +
            ", competitionName='" + getCompetitionName() + "'" +
            "}";
    }
}
