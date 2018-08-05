package nl.bluepers.app.service.mapper;

import nl.bluepers.app.domain.*;
import nl.bluepers.app.service.dto.SpelerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Speler and its DTO SpelerDTO.
 */
@Mapper(componentModel = "spring", uses = {WedstrijdMapper.class})
public interface SpelerMapper extends EntityMapper<SpelerDTO, Speler> {

    @Mapping(source = "wedstrijd.id", target = "wedstrijdId")
    SpelerDTO toDto(Speler speler);

    @Mapping(source = "wedstrijdId", target = "wedstrijd")
    Speler toEntity(SpelerDTO spelerDTO);

    default Speler fromId(Long id) {
        if (id == null) {
            return null;
        }
        Speler speler = new Speler();
        speler.setId(id);
        return speler;
    }
}
