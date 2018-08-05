package nl.bluepers.app.service;

import nl.bluepers.app.service.dto.SpelerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Speler.
 */
public interface SpelerService {

    /**
     * Save a speler.
     *
     * @param spelerDTO the entity to save
     * @return the persisted entity
     */
    SpelerDTO save(SpelerDTO spelerDTO);

    /**
     * Get all the spelers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SpelerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" speler.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SpelerDTO> findOne(Long id);

    /**
     * Delete the "id" speler.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
