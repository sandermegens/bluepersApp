package nl.bluepers.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "naam")
    private String naam;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "team_competitie",
               joinColumns = @JoinColumn(name = "teams_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "competities_id", referencedColumnName = "id"))
    private Set<Competitie> competities = new HashSet<>();

    @OneToMany(mappedBy = "team")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Speler> spelers = new HashSet<>();

    @ManyToMany(mappedBy = "teams")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Wedstrijd> wedstrijds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public Team naam(String naam) {
        this.naam = naam;
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Competitie> getCompetities() {
        return competities;
    }

    public Team competities(Set<Competitie> competities) {
        this.competities = competities;
        return this;
    }

    public Team addCompetitie(Competitie competitie) {
        this.competities.add(competitie);
        competitie.getTeams().add(this);
        return this;
    }

    public Team removeCompetitie(Competitie competitie) {
        this.competities.remove(competitie);
        competitie.getTeams().remove(this);
        return this;
    }

    public void setCompetities(Set<Competitie> competities) {
        this.competities = competities;
    }

    public Set<Speler> getSpelers() {
        return spelers;
    }

    public Team spelers(Set<Speler> spelers) {
        this.spelers = spelers;
        return this;
    }

    public Team addSpeler(Speler speler) {
        this.spelers.add(speler);
        speler.setTeam(this);
        return this;
    }

    public Team removeSpeler(Speler speler) {
        this.spelers.remove(speler);
        speler.setTeam(null);
        return this;
    }

    public void setSpelers(Set<Speler> spelers) {
        this.spelers = spelers;
    }

    public Set<Wedstrijd> getWedstrijds() {
        return wedstrijds;
    }

    public Team wedstrijds(Set<Wedstrijd> wedstrijds) {
        this.wedstrijds = wedstrijds;
        return this;
    }

    public Team addWedstrijd(Wedstrijd wedstrijd) {
        this.wedstrijds.add(wedstrijd);
        wedstrijd.getTeams().add(this);
        return this;
    }

    public Team removeWedstrijd(Wedstrijd wedstrijd) {
        this.wedstrijds.remove(wedstrijd);
        wedstrijd.getTeams().remove(this);
        return this;
    }

    public void setWedstrijds(Set<Wedstrijd> wedstrijds) {
        this.wedstrijds = wedstrijds;
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
        Team team = (Team) o;
        if (team.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), team.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
