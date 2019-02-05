package com.pesdoamendoim.competition.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Competition.
 */
@Entity
@Table(name = "competition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Competition implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "competition_name", nullable = false)
    private String competitionName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public Competition competitionName(String competitionName) {
        this.competitionName = competitionName;
        return this;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Competition competition = (Competition) o;
        if (competition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Competition{" +
            "id=" + getId() +
            ", competitionName='" + getCompetitionName() + "'" +
            "}";
    }
}
