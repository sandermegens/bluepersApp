package nl.bluepers.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import nl.bluepers.app.domain.enumeration.Positie;

/**
 * A Speler.
 */
@Entity
@Table(name = "speler")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Speler implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voornaam")
    private String voornaam;

    @Column(name = "tussenvoegsel")
    private String tussenvoegsel;

    @Column(name = "achternaam")
    private String achternaam;

    @Column(name = "rugnummer")
    private Integer rugnummer;

    @Enumerated(EnumType.STRING)
    @Column(name = "positie")
    private Positie positie;

    @NotNull
    @Column(name = "geboorte_datum", nullable = false)
    private LocalDate geboorteDatum;

    @Column(name = "debuut")
    private LocalDate debuut;

    @Column(name = "bijzonderheden")
    private String bijzonderheden;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Wedstrijd wedstrijd;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public Speler voornaam(String voornaam) {
        this.voornaam = voornaam;
        return this;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public Speler tussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
        return this;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public Speler achternaam(String achternaam) {
        this.achternaam = achternaam;
        return this;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Integer getRugnummer() {
        return rugnummer;
    }

    public Speler rugnummer(Integer rugnummer) {
        this.rugnummer = rugnummer;
        return this;
    }

    public void setRugnummer(Integer rugnummer) {
        this.rugnummer = rugnummer;
    }

    public Positie getPositie() {
        return positie;
    }

    public Speler positie(Positie positie) {
        this.positie = positie;
        return this;
    }

    public void setPositie(Positie positie) {
        this.positie = positie;
    }

    public LocalDate getGeboorteDatum() {
        return geboorteDatum;
    }

    public Speler geboorteDatum(LocalDate geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
        return this;
    }

    public void setGeboorteDatum(LocalDate geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public LocalDate getDebuut() {
        return debuut;
    }

    public Speler debuut(LocalDate debuut) {
        this.debuut = debuut;
        return this;
    }

    public void setDebuut(LocalDate debuut) {
        this.debuut = debuut;
    }

    public String getBijzonderheden() {
        return bijzonderheden;
    }

    public Speler bijzonderheden(String bijzonderheden) {
        this.bijzonderheden = bijzonderheden;
        return this;
    }

    public void setBijzonderheden(String bijzonderheden) {
        this.bijzonderheden = bijzonderheden;
    }

    public Wedstrijd getWedstrijd() {
        return wedstrijd;
    }

    public Speler wedstrijd(Wedstrijd wedstrijd) {
        this.wedstrijd = wedstrijd;
        return this;
    }

    public void setWedstrijd(Wedstrijd wedstrijd) {
        this.wedstrijd = wedstrijd;
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
        Speler speler = (Speler) o;
        if (speler.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), speler.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Speler{" +
            "id=" + getId() +
            ", voornaam='" + getVoornaam() + "'" +
            ", tussenvoegsel='" + getTussenvoegsel() + "'" +
            ", achternaam='" + getAchternaam() + "'" +
            ", rugnummer=" + getRugnummer() +
            ", positie='" + getPositie() + "'" +
            ", geboorteDatum='" + getGeboorteDatum() + "'" +
            ", debuut='" + getDebuut() + "'" +
            ", bijzonderheden='" + getBijzonderheden() + "'" +
            "}";
    }
}
