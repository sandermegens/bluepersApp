package nl.bluepers.app.service;

import nl.bluepers.app.service.dto.CompetitieDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Competitie.
 */
public interface CompetitieService {

    /**
     * Save a competitie.
     *
     * @param competitieDTO the entity to save
     * @return the persisted entity
     */
    CompetitieDTO save(CompetitieDTO competitieDTO);

    /**
     * Get all the competities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CompetitieDTO> findAll(Pageable pageable);


    /**
     * Get the "id" competitie.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CompetitieDTO> findOne(Long id);

    /**
     * Delete the "id" competitie.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
