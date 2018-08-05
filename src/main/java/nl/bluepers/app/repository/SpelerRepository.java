package nl.bluepers.app.repository;

import nl.bluepers.app.domain.Speler;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Speler entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpelerRepository extends JpaRepository<Speler, Long> {

}
