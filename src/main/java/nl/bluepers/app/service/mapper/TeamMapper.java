package nl.bluepers.app.service.mapper;

import nl.bluepers.app.domain.*;
import nl.bluepers.app.service.dto.TeamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Team and its DTO TeamDTO.
 */
@Mapper(componentModel = "spring", uses = {CompetitieMapper.class})
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {


    @Mapping(target = "spelers", ignore = true)
    @Mapping(target = "wedstrijds", ignore = true)
    Team toEntity(TeamDTO teamDTO);

    default Team fromId(Long id) {
        if (id == null) {
            return null;
        }
        Team team = new Team();
        team.setId(id);
        return team;
    }
}
