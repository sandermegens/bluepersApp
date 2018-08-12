package nl.bluepers.app.service.mapper;

import nl.bluepers.app.domain.*;
import nl.bluepers.app.service.dto.WedstrijdDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Wedstrijd and its DTO WedstrijdDTO.
 */
@Mapper(componentModel = "spring", uses = {CompetitieMapper.class, TeamMapper.class})
public interface WedstrijdMapper extends EntityMapper<WedstrijdDTO, Wedstrijd> {

    @Mapping(source = "competitie.id", target = "competitieId")
    WedstrijdDTO toDto(Wedstrijd wedstrijd);

    @Mapping(source = "competitieId", target = "competitie")
    Wedstrijd toEntity(WedstrijdDTO wedstrijdDTO);

    default Wedstrijd fromId(Long id) {
        if (id == null) {
            return null;
        }
        Wedstrijd wedstrijd = new Wedstrijd();
        wedstrijd.setId(id);
        return wedstrijd;
    }
}
