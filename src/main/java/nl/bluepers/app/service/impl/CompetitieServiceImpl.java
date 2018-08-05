package nl.bluepers.app.service.impl;

import nl.bluepers.app.service.CompetitieService;
import nl.bluepers.app.domain.Competitie;
import nl.bluepers.app.repository.CompetitieRepository;
import nl.bluepers.app.service.dto.CompetitieDTO;
import nl.bluepers.app.service.mapper.CompetitieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Competitie.
 */
@Service
@Transactional
public class CompetitieServiceImpl implements CompetitieService {

    private final Logger log = LoggerFactory.getLogger(CompetitieServiceImpl.class);

    private final CompetitieRepository competitieRepository;

    private final CompetitieMapper competitieMapper;

    public CompetitieServiceImpl(CompetitieRepository competitieRepository, CompetitieMapper competitieMapper) {
        this.competitieRepository = competitieRepository;
        this.competitieMapper = competitieMapper;
    }

    /**
     * Save a competitie.
     *
     * @param competitieDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompetitieDTO save(CompetitieDTO competitieDTO) {
        log.debug("Request to save Competitie : {}", competitieDTO);
        Competitie competitie = competitieMapper.toEntity(competitieDTO);
        competitie = competitieRepository.save(competitie);
        return competitieMapper.toDto(competitie);
    }

    /**
     * Get all the competities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompetitieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Competities");
        return competitieRepository.findAll(pageable)
            .map(competitieMapper::toDto);
    }


    /**
     * Get one competitie by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompetitieDTO> findOne(Long id) {
        log.debug("Request to get Competitie : {}", id);
        return competitieRepository.findById(id)
            .map(competitieMapper::toDto);
    }

    /**
     * Delete the competitie by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Competitie : {}", id);
        competitieRepository.deleteById(id);
    }
}
