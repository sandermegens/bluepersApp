package nl.bluepers.app.service;

import nl.bluepers.app.service.dto.WedstrijdDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Wedstrijd.
 */
public interface WedstrijdService {

    /**
     * Save a wedstrijd.
     *
     * @param wedstrijdDTO the entity to save
     * @return the persisted entity
     */
    WedstrijdDTO save(WedstrijdDTO wedstrijdDTO);

    /**
     * Get all the wedstrijds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WedstrijdDTO> findAll(Pageable pageable);


    /**
     * Get the "id" wedstrijd.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<WedstrijdDTO> findOne(Long id);

    /**
     * Delete the "id" wedstrijd.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
