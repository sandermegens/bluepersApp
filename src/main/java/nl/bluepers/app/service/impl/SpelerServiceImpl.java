package nl.bluepers.app.service.impl;

import nl.bluepers.app.service.SpelerService;
import nl.bluepers.app.domain.Speler;
import nl.bluepers.app.repository.SpelerRepository;
import nl.bluepers.app.service.dto.SpelerDTO;
import nl.bluepers.app.service.mapper.SpelerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Speler.
 */
@Service
@Transactional
public class SpelerServiceImpl implements SpelerService {

    private final Logger log = LoggerFactory.getLogger(SpelerServiceImpl.class);

    private final SpelerRepository spelerRepository;

    private final SpelerMapper spelerMapper;

    public SpelerServiceImpl(SpelerRepository spelerRepository, SpelerMapper spelerMapper) {
        this.spelerRepository = spelerRepository;
        this.spelerMapper = spelerMapper;
    }

    /**
     * Save a speler.
     *
     * @param spelerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SpelerDTO save(SpelerDTO spelerDTO) {
        log.debug("Request to save Speler : {}", spelerDTO);
        Speler speler = spelerMapper.toEntity(spelerDTO);
        speler = spelerRepository.save(speler);
        return spelerMapper.toDto(speler);
    }

    /**
     * Get all the spelers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SpelerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Spelers");
        return spelerRepository.findAll(pageable)
            .map(spelerMapper::toDto);
    }


    /**
     * Get one speler by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SpelerDTO> findOne(Long id) {
        log.debug("Request to get Speler : {}", id);
        return spelerRepository.findById(id)
            .map(spelerMapper::toDto);
    }

    /**
     * Delete the speler by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Speler : {}", id);
        spelerRepository.deleteById(id);
    }
}
