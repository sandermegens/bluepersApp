package nl.bluepers.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import nl.bluepers.app.domain.enumeration.Positie;

/**
 * A DTO for the Speler entity.
 */
public class SpelerDTO implements Serializable {

    private Long id;

    private String voornaam;

    private String tussenvoegsel;

    private String achternaam;

    private Integer rugnummer;

    private Positie positie;

    @NotNull
    private LocalDate geboorteDatum;

    private LocalDate debuut;

    private String bijzonderheden;

    private Long teamId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Integer getRugnummer() {
        return rugnummer;
    }

    public void setRugnummer(Integer rugnummer) {
        this.rugnummer = rugnummer;
    }

    public Positie getPositie() {
        return positie;
    }

    public void setPositie(Positie positie) {
        this.positie = positie;
    }

    public LocalDate getGeboorteDatum() {
        return geboorteDatum;
    }

    public void setGeboorteDatum(LocalDate geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public LocalDate getDebuut() {
        return debuut;
    }

    public void setDebuut(LocalDate debuut) {
        this.debuut = debuut;
    }

    public String getBijzonderheden() {
        return bijzonderheden;
    }

    public void setBijzonderheden(String bijzonderheden) {
        this.bijzonderheden = bijzonderheden;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpelerDTO spelerDTO = (SpelerDTO) o;
        if (spelerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), spelerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SpelerDTO{" +
            "id=" + getId() +
            ", voornaam='" + getVoornaam() + "'" +
            ", tussenvoegsel='" + getTussenvoegsel() + "'" +
            ", achternaam='" + getAchternaam() + "'" +
            ", rugnummer=" + getRugnummer() +
            ", positie='" + getPositie() + "'" +
            ", geboorteDatum='" + getGeboorteDatum() + "'" +
            ", debuut='" + getDebuut() + "'" +
            ", bijzonderheden='" + getBijzonderheden() + "'" +
            ", team=" + getTeamId() +
            "}";
    }
}
