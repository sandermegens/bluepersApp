package nl.bluepers.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Wedstrijd.
 */
@Entity
@Table(name = "wedstrijd")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Wedstrijd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    @NotNull
    @Column(name = "tijd", nullable = false)
    private LocalDate tijd;

    @Column(name = "plaats")
    private String plaats;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Competitie competitie;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "wedstrijd_team",
               joinColumns = @JoinColumn(name = "wedstrijds_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "teams_id", referencedColumnName = "id"))
    private Set<Team> teams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public Wedstrijd datum(LocalDate datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalDate getTijd() {
        return tijd;
    }

    public Wedstrijd tijd(LocalDate tijd) {
        this.tijd = tijd;
        return this;
    }

    public void setTijd(LocalDate tijd) {
        this.tijd = tijd;
    }

    public String getPlaats() {
        return plaats;
    }

    public Wedstrijd plaats(String plaats) {
        this.plaats = plaats;
        return this;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public Competitie getCompetitie() {
        return competitie;
    }

    public Wedstrijd competitie(Competitie competitie) {
        this.competitie = competitie;
        return this;
    }

    public void setCompetitie(Competitie competitie) {
        this.competitie = competitie;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Wedstrijd teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public Wedstrijd addTeam(Team team) {
        this.teams.add(team);
        team.getWedstrijds().add(this);
        return this;
    }

    public Wedstrijd removeTeam(Team team) {
        this.teams.remove(team);
        team.getWedstrijds().remove(this);
        return this;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
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
        Wedstrijd wedstrijd = (Wedstrijd) o;
        if (wedstrijd.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wedstrijd.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Wedstrijd{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", tijd='" + getTijd() + "'" +
            ", plaats='" + getPlaats() + "'" +
            "}";
    }
}
