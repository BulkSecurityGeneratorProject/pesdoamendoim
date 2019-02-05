package com.pesdoamendoim.competition.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Round.
 */
@Entity
@Table(name = "round")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Round implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "round_name", nullable = false)
    private String roundName;

    @ManyToOne
    @JsonIgnoreProperties("rounds")
    private Groupe groupe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoundName() {
        return roundName;
    }

    public Round roundName(String roundName) {
        this.roundName = roundName;
        return this;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public Round groupe(Groupe groupe) {
        this.groupe = groupe;
        return this;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
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
        Round round = (Round) o;
        if (round.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), round.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Round{" +
            "id=" + getId() +
            ", roundName='" + getRoundName() + "'" +
            "}";
    }
}
