package nl.bluepers.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Competitie entity.
 */
public class CompetitieDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 9, max = 9)
    @Pattern(regexp = "(\\d{4}-\\d{4})")
    private String seizoen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeizoen() {
        return seizoen;
    }

    public void setSeizoen(String seizoen) {
        this.seizoen = seizoen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompetitieDTO competitieDTO = (CompetitieDTO) o;
        if (competitieDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competitieDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompetitieDTO{" +
            "id=" + getId() +
            ", seizoen='" + getSeizoen() + "'" +
            "}";
    }
}
