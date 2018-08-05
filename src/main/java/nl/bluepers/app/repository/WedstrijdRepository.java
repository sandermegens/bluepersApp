package nl.bluepers.app.repository;

import nl.bluepers.app.domain.Wedstrijd;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Wedstrijd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WedstrijdRepository extends JpaRepository<Wedstrijd, Long> {

}
