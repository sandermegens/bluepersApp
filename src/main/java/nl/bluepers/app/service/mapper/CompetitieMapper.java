package nl.bluepers.app.service.mapper;

import nl.bluepers.app.domain.*;
import nl.bluepers.app.service.dto.CompetitieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Competitie and its DTO CompetitieDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompetitieMapper extends EntityMapper<CompetitieDTO, Competitie> {


    @Mapping(target = "wedstrijds", ignore = true)
    @Mapping(target = "teams", ignore = true)
    Competitie toEntity(CompetitieDTO competitieDTO);

    default Competitie fromId(Long id) {
        if (id == null) {
            return null;
        }
        Competitie competitie = new Competitie();
        competitie.setId(id);
        return competitie;
    }
}
