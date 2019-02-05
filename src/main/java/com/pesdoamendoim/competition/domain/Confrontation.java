package com.pesdoamendoim.competition.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Confrontation.
 */
@Entity
@Table(name = "confrontation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Confrontation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "data_confronto")
    private Instant dataConfronto;

    @Column(name = "score_player_1")
    private Integer scorePlayer1;

    @Column(name = "score_player_2")
    private Integer scorePlayer2;

    @Column(name = "penalty_player_1")
    private Integer penaltyPlayer1;

    @Column(name = "penalty_player_2")
    private Integer penaltyPlayer2;

    @OneToOne
    @JoinColumn(unique = true)
    private Player player1;

    @OneToOne
    @JoinColumn(unique = true)
    private Player player2;

    @ManyToOne
    @JsonIgnoreProperties("confrontations")
    private Round round;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataConfronto() {
        return dataConfronto;
    }

    public Confrontation dataConfronto(Instant dataConfronto) {
        this.dataConfronto = dataConfronto;
        return this;
    }

    public void setDataConfronto(Instant dataConfronto) {
        this.dataConfronto = dataConfronto;
    }

    public Integer getScorePlayer1() {
        return scorePlayer1;
    }

    public Confrontation scorePlayer1(Integer scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
        return this;
    }

    public void setScorePlayer1(Integer scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public Integer getScorePlayer2() {
        return scorePlayer2;
    }

    public Confrontation scorePlayer2(Integer scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
        return this;
    }

    public void setScorePlayer2(Integer scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }

    public Integer getPenaltyPlayer1() {
        return penaltyPlayer1;
    }

    public Confrontation penaltyPlayer1(Integer penaltyPlayer1) {
        this.penaltyPlayer1 = penaltyPlayer1;
        return this;
    }

    public void setPenaltyPlayer1(Integer penaltyPlayer1) {
        this.penaltyPlayer1 = penaltyPlayer1;
    }

    public Integer getPenaltyPlayer2() {
        return penaltyPlayer2;
    }

    public Confrontation penaltyPlayer2(Integer penaltyPlayer2) {
        this.penaltyPlayer2 = penaltyPlayer2;
        return this;
    }

    public void setPenaltyPlayer2(Integer penaltyPlayer2) {
        this.penaltyPlayer2 = penaltyPlayer2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Confrontation player1(Player player) {
        this.player1 = player;
        return this;
    }

    public void setPlayer1(Player player) {
        this.player1 = player;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Confrontation player2(Player player) {
        this.player2 = player;
        return this;
    }

    public void setPlayer2(Player player) {
        this.player2 = player;
    }

    public Round getRound() {
        return round;
    }

    public Confrontation round(Round round) {
        this.round = round;
        return this;
    }

    public void setRound(Round round) {
        this.round = round;
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
        Confrontation confrontation = (Confrontation) o;
        if (confrontation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), confrontation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Confrontation{" +
            "id=" + getId() +
            ", dataConfronto='" + getDataConfronto() + "'" +
            ", scorePlayer1=" + getScorePlayer1() +
            ", scorePlayer2=" + getScorePlayer2() +
            ", penaltyPlayer1=" + getPenaltyPlayer1() +
            ", penaltyPlayer2=" + getPenaltyPlayer2() +
            "}";
    }
}
