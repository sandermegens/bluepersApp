package nl.bluepers.app.repository;

import nl.bluepers.app.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Team entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query(value = "select distinct team from Team team left join fetch team.competities",
        countQuery = "select count(distinct team) from Team team")
    Page<Team> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct team from Team team left join fetch team.competities")
    List<Team> findAllWithEagerRelationships();

    @Query("select team from Team team left join fetch team.competities where team.id =:id")
    Optional<Team> findOneWithEagerRelationships(@Param("id") Long id);

}
