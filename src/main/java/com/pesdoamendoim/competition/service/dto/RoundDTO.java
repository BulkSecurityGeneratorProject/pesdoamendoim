package com.pesdoamendoim.competition.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Round entity.
 */
public class RoundDTO implements Serializable {

    private Long id;

    @NotNull
    private String roundName;


    private Long groupeId;

    private String groupeGroupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public Long getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(Long groupeId) {
        this.groupeId = groupeId;
    }

    public String getGroupeGroupName() {
        return groupeGroupName;
    }

    public void setGroupeGroupName(String groupeGroupName) {
        this.groupeGroupName = groupeGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoundDTO roundDTO = (RoundDTO) o;
        if (roundDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roundDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoundDTO{" +
            "id=" + getId() +
            ", roundName='" + getRoundName() + "'" +
            ", groupe=" + getGroupeId() +
            ", groupe='" + getGroupeGroupName() + "'" +
            "}";
    }
}
