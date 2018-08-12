package nl.bluepers.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Wedstrijd entity.
 */
public class WedstrijdDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate datum;

    @NotNull
    private LocalDate tijd;

    private String plaats;

    private Long competitieId;

    private Set<TeamDTO> teams = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalDate getTijd() {
        return tijd;
    }

    public void setTijd(LocalDate tijd) {
        this.tijd = tijd;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public Long getCompetitieId() {
        return competitieId;
    }

    public void setCompetitieId(Long competitieId) {
        this.competitieId = competitieId;
    }

    public Set<TeamDTO> getTeams() {
        return teams;
    }

    public void setTeams(Set<TeamDTO> teams) {
        this.teams = teams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WedstrijdDTO wedstrijdDTO = (WedstrijdDTO) o;
        if (wedstrijdDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wedstrijdDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WedstrijdDTO{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", tijd='" + getTijd() + "'" +
            ", plaats='" + getPlaats() + "'" +
            ", competitie=" + getCompetitieId() +
            "}";
    }
}
