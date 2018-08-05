package nl.bluepers.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import nl.bluepers.app.service.CompetitieService;
import nl.bluepers.app.web.rest.errors.BadRequestAlertException;
import nl.bluepers.app.web.rest.util.HeaderUtil;
import nl.bluepers.app.web.rest.util.PaginationUtil;
import nl.bluepers.app.service.dto.CompetitieDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Competitie.
 */
@RestController
@RequestMapping("/api")
public class CompetitieResource {

    private final Logger log = LoggerFactory.getLogger(CompetitieResource.class);

    private static final String ENTITY_NAME = "competitie";

    private final CompetitieService competitieService;

    public CompetitieResource(CompetitieService competitieService) {
        this.competitieService = competitieService;
    }

    /**
     * POST  /competities : Create a new competitie.
     *
     * @param competitieDTO the competitieDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new competitieDTO, or with status 400 (Bad Request) if the competitie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/competities")
    @Timed
    public ResponseEntity<CompetitieDTO> createCompetitie(@Valid @RequestBody CompetitieDTO competitieDTO) throws URISyntaxException {
        log.debug("REST request to save Competitie : {}", competitieDTO);
        if (competitieDTO.getId() != null) {
            throw new BadRequestAlertException("A new competitie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitieDTO result = competitieService.save(competitieDTO);
        return ResponseEntity.created(new URI("/api/competities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /competities : Updates an existing competitie.
     *
     * @param competitieDTO the competitieDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated competitieDTO,
     * or with status 400 (Bad Request) if the competitieDTO is not valid,
     * or with status 500 (Internal Server Error) if the competitieDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/competities")
    @Timed
    public ResponseEntity<CompetitieDTO> updateCompetitie(@Valid @RequestBody CompetitieDTO competitieDTO) throws URISyntaxException {
        log.debug("REST request to update Competitie : {}", competitieDTO);
        if (competitieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompetitieDTO result = competitieService.save(competitieDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, competitieDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /competities : get all the competities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of competities in body
     */
    @GetMapping("/competities")
    @Timed
    public ResponseEntity<List<CompetitieDTO>> getAllCompetities(Pageable pageable) {
        log.debug("REST request to get a page of Competities");
        Page<CompetitieDTO> page = competitieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/competities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /competities/:id : get the "id" competitie.
     *
     * @param id the id of the competitieDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the competitieDTO, or with status 404 (Not Found)
     */
    @GetMapping("/competities/{id}")
    @Timed
    public ResponseEntity<CompetitieDTO> getCompetitie(@PathVariable Long id) {
        log.debug("REST request to get Competitie : {}", id);
        Optional<CompetitieDTO> competitieDTO = competitieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competitieDTO);
    }

    /**
     * DELETE  /competities/:id : delete the "id" competitie.
     *
     * @param id the id of the competitieDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/competities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompetitie(@PathVariable Long id) {
        log.debug("REST request to delete Competitie : {}", id);
        competitieService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
