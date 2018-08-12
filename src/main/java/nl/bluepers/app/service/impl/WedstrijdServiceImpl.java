package nl.bluepers.app.service.impl;

import nl.bluepers.app.service.WedstrijdService;
import nl.bluepers.app.domain.Wedstrijd;
import nl.bluepers.app.repository.WedstrijdRepository;
import nl.bluepers.app.service.dto.WedstrijdDTO;
import nl.bluepers.app.service.mapper.WedstrijdMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Wedstrijd.
 */
@Service
@Transactional
public class WedstrijdServiceImpl implements WedstrijdService {

    private final Logger log = LoggerFactory.getLogger(WedstrijdServiceImpl.class);

    private final WedstrijdRepository wedstrijdRepository;

    private final WedstrijdMapper wedstrijdMapper;

    public WedstrijdServiceImpl(WedstrijdRepository wedstrijdRepository, WedstrijdMapper wedstrijdMapper) {
        this.wedstrijdRepository = wedstrijdRepository;
        this.wedstrijdMapper = wedstrijdMapper;
    }

    /**
     * Save a wedstrijd.
     *
     * @param wedstrijdDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WedstrijdDTO save(WedstrijdDTO wedstrijdDTO) {
        log.debug("Request to save Wedstrijd : {}", wedstrijdDTO);
        Wedstrijd wedstrijd = wedstrijdMapper.toEntity(wedstrijdDTO);
        wedstrijd = wedstrijdRepository.save(wedstrijd);
        return wedstrijdMapper.toDto(wedstrijd);
    }

    /**
     * Get all the wedstrijds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WedstrijdDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Wedstrijds");
        return wedstrijdRepository.findAll(pageable)
            .map(wedstrijdMapper::toDto);
    }

    /**
     * Get all the Wedstrijd with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<WedstrijdDTO> findAllWithEagerRelationships(Pageable pageable) {
        return wedstrijdRepository.findAllWithEagerRelationships(pageable).map(wedstrijdMapper::toDto);
    }
    

    /**
     * Get one wedstrijd by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WedstrijdDTO> findOne(Long id) {
        log.debug("Request to get Wedstrijd : {}", id);
        return wedstrijdRepository.findOneWithEagerRelationships(id)
            .map(wedstrijdMapper::toDto);
    }

    /**
     * Delete the wedstrijd by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Wedstrijd : {}", id);
        wedstrijdRepository.deleteById(id);
    }
}
