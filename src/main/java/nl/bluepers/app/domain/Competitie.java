package nl.bluepers.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Competitie.
 */
@Entity
@Table(name = "competitie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Competitie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 9, max = 9)
    @Pattern(regexp = "(\\d{4}-\\d{4})")
    @Column(name = "seizoen", length = 9, nullable = false)
    private String seizoen;

    @OneToMany(mappedBy = "competitie")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Wedstrijd> wedstrijds = new HashSet<>();

    @ManyToMany(mappedBy = "competities")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Team> teams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeizoen() {
        return seizoen;
    }

    public Competitie seizoen(String seizoen) {
        this.seizoen = seizoen;
        return this;
    }

    public void setSeizoen(String seizoen) {
        this.seizoen = seizoen;
    }

    public Set<Wedstrijd> getWedstrijds() {
        return wedstrijds;
    }

    public Competitie wedstrijds(Set<Wedstrijd> wedstrijds) {
        this.wedstrijds = wedstrijds;
        return this;
    }

    public Competitie addWedstrijd(Wedstrijd wedstrijd) {
        this.wedstrijds.add(wedstrijd);
        wedstrijd.setCompetitie(this);
        return this;
    }

    public Competitie removeWedstrijd(Wedstrijd wedstrijd) {
        this.wedstrijds.remove(wedstrijd);
        wedstrijd.setCompetitie(null);
        return this;
    }

    public void setWedstrijds(Set<Wedstrijd> wedstrijds) {
        this.wedstrijds = wedstrijds;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Competitie teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public Competitie addTeam(Team team) {
        this.teams.add(team);
        team.getCompetities().add(this);
        return this;
    }

    public Competitie removeTeam(Team team) {
        this.teams.remove(team);
        team.getCompetities().remove(this);
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
        Competitie competitie = (Competitie) o;
        if (competitie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competitie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Competitie{" +
            "id=" + getId() +
            ", seizoen='" + getSeizoen() + "'" +
            "}";
    }
}
