package nl.bluepers.app.repository;

import nl.bluepers.app.domain.Competitie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Competitie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitieRepository extends JpaRepository<Competitie, Long> {

}
