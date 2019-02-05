package com.pesdoamendoim.competition.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Confrontation entity.
 */
public class ConfrontationDTO implements Serializable {

    private Long id;

    private Instant dataConfronto;

    private Integer scorePlayer1;

    private Integer scorePlayer2;

    private Integer penaltyPlayer1;

    private Integer penaltyPlayer2;


    private Long player1Id;

    private String player1PlayerName;

    private Long player2Id;

    private String player2PlayerName;

    private Long roundId;

    private String roundRoundName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataConfronto() {
        return dataConfronto;
    }

    public void setDataConfronto(Instant dataConfronto) {
        this.dataConfronto = dataConfronto;
    }

    public Integer getScorePlayer1() {
        return scorePlayer1;
    }

    public void setScorePlayer1(Integer scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public Integer getScorePlayer2() {
        return scorePlayer2;
    }

    public void setScorePlayer2(Integer scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }

    public Integer getPenaltyPlayer1() {
        return penaltyPlayer1;
    }

    public void setPenaltyPlayer1(Integer penaltyPlayer1) {
        this.penaltyPlayer1 = penaltyPlayer1;
    }

    public Integer getPenaltyPlayer2() {
        return penaltyPlayer2;
    }

    public void setPenaltyPlayer2(Integer penaltyPlayer2) {
        this.penaltyPlayer2 = penaltyPlayer2;
    }

    public Long getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(Long playerId) {
        this.player1Id = playerId;
    }

    public String getPlayer1PlayerName() {
        return player1PlayerName;
    }

    public void setPlayer1PlayerName(String playerPlayerName) {
        this.player1PlayerName = playerPlayerName;
    }

    public Long getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(Long playerId) {
        this.player2Id = playerId;
    }

    public String getPlayer2PlayerName() {
        return player2PlayerName;
    }

    public void setPlayer2PlayerName(String playerPlayerName) {
        this.player2PlayerName = playerPlayerName;
    }

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public String getRoundRoundName() {
        return roundRoundName;
    }

    public void setRoundRoundName(String roundRoundName) {
        this.roundRoundName = roundRoundName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConfrontationDTO confrontationDTO = (ConfrontationDTO) o;
        if (confrontationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), confrontationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConfrontationDTO{" +
            "id=" + getId() +
            ", dataConfronto='" + getDataConfronto() + "'" +
            ", scorePlayer1=" + getScorePlayer1() +
            ", scorePlayer2=" + getScorePlayer2() +
            ", penaltyPlayer1=" + getPenaltyPlayer1() +
            ", penaltyPlayer2=" + getPenaltyPlayer2() +
            ", player1=" + getPlayer1Id() +
            ", player1='" + getPlayer1PlayerName() + "'" +
            ", player2=" + getPlayer2Id() +
            ", player2='" + getPlayer2PlayerName() + "'" +
            ", round=" + getRoundId() +
            ", round='" + getRoundRoundName() + "'" +
            "}";
    }
}
