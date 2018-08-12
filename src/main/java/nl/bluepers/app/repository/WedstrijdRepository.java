package nl.bluepers.app.repository;

import nl.bluepers.app.domain.Wedstrijd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Wedstrijd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WedstrijdRepository extends JpaRepository<Wedstrijd, Long> {

    @Query(value = "select distinct wedstrijd from Wedstrijd wedstrijd left join fetch wedstrijd.teams",
        countQuery = "select count(distinct wedstrijd) from Wedstrijd wedstrijd")
    Page<Wedstrijd> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct wedstrijd from Wedstrijd wedstrijd left join fetch wedstrijd.teams")
    List<Wedstrijd> findAllWithEagerRelationships();

    @Query("select wedstrijd from Wedstrijd wedstrijd left join fetch wedstrijd.teams where wedstrijd.id =:id")
    Optional<Wedstrijd> findOneWithEagerRelationships(@Param("id") Long id);

}
