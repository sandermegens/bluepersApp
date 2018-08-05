package nl.bluepers.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import nl.bluepers.app.service.SpelerService;
import nl.bluepers.app.web.rest.errors.BadRequestAlertException;
import nl.bluepers.app.web.rest.util.HeaderUtil;
import nl.bluepers.app.web.rest.util.PaginationUtil;
import nl.bluepers.app.service.dto.SpelerDTO;
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
 * REST controller for managing Speler.
 */
@RestController
@RequestMapping("/api")
public class SpelerResource {

    private final Logger log = LoggerFactory.getLogger(SpelerResource.class);

    private static final String ENTITY_NAME = "speler";

    private final SpelerService spelerService;

    public SpelerResource(SpelerService spelerService) {
        this.spelerService = spelerService;
    }

    /**
     * POST  /spelers : Create a new speler.
     *
     * @param spelerDTO the spelerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new spelerDTO, or with status 400 (Bad Request) if the speler has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/spelers")
    @Timed
    public ResponseEntity<SpelerDTO> createSpeler(@Valid @RequestBody SpelerDTO spelerDTO) throws URISyntaxException {
        log.debug("REST request to save Speler : {}", spelerDTO);
        if (spelerDTO.getId() != null) {
            throw new BadRequestAlertException("A new speler cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpelerDTO result = spelerService.save(spelerDTO);
        return ResponseEntity.created(new URI("/api/spelers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /spelers : Updates an existing speler.
     *
     * @param spelerDTO the spelerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated spelerDTO,
     * or with status 400 (Bad Request) if the spelerDTO is not valid,
     * or with status 500 (Internal Server Error) if the spelerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/spelers")
    @Timed
    public ResponseEntity<SpelerDTO> updateSpeler(@Valid @RequestBody SpelerDTO spelerDTO) throws URISyntaxException {
        log.debug("REST request to update Speler : {}", spelerDTO);
        if (spelerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SpelerDTO result = spelerService.save(spelerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, spelerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /spelers : get all the spelers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of spelers in body
     */
    @GetMapping("/spelers")
    @Timed
    public ResponseEntity<List<SpelerDTO>> getAllSpelers(Pageable pageable) {
        log.debug("REST request to get a page of Spelers");
        Page<SpelerDTO> page = spelerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/spelers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /spelers/:id : get the "id" speler.
     *
     * @param id the id of the spelerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the spelerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/spelers/{id}")
    @Timed
    public ResponseEntity<SpelerDTO> getSpeler(@PathVariable Long id) {
        log.debug("REST request to get Speler : {}", id);
        Optional<SpelerDTO> spelerDTO = spelerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(spelerDTO);
    }

    /**
     * DELETE  /spelers/:id : delete the "id" speler.
     *
     * @param id the id of the spelerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/spelers/{id}")
    @Timed
    public ResponseEntity<Void> deleteSpeler(@PathVariable Long id) {
        log.debug("REST request to delete Speler : {}", id);
        spelerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
