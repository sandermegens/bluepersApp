package nl.bluepers.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import nl.bluepers.app.service.WedstrijdService;
import nl.bluepers.app.web.rest.errors.BadRequestAlertException;
import nl.bluepers.app.web.rest.util.HeaderUtil;
import nl.bluepers.app.web.rest.util.PaginationUtil;
import nl.bluepers.app.service.dto.WedstrijdDTO;
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
 * REST controller for managing Wedstrijd.
 */
@RestController
@RequestMapping("/api")
public class WedstrijdResource {

    private final Logger log = LoggerFactory.getLogger(WedstrijdResource.class);

    private static final String ENTITY_NAME = "wedstrijd";

    private final WedstrijdService wedstrijdService;

    public WedstrijdResource(WedstrijdService wedstrijdService) {
        this.wedstrijdService = wedstrijdService;
    }

    /**
     * POST  /wedstrijds : Create a new wedstrijd.
     *
     * @param wedstrijdDTO the wedstrijdDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wedstrijdDTO, or with status 400 (Bad Request) if the wedstrijd has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wedstrijds")
    @Timed
    public ResponseEntity<WedstrijdDTO> createWedstrijd(@Valid @RequestBody WedstrijdDTO wedstrijdDTO) throws URISyntaxException {
        log.debug("REST request to save Wedstrijd : {}", wedstrijdDTO);
        if (wedstrijdDTO.getId() != null) {
            throw new BadRequestAlertException("A new wedstrijd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WedstrijdDTO result = wedstrijdService.save(wedstrijdDTO);
        return ResponseEntity.created(new URI("/api/wedstrijds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wedstrijds : Updates an existing wedstrijd.
     *
     * @param wedstrijdDTO the wedstrijdDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wedstrijdDTO,
     * or with status 400 (Bad Request) if the wedstrijdDTO is not valid,
     * or with status 500 (Internal Server Error) if the wedstrijdDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wedstrijds")
    @Timed
    public ResponseEntity<WedstrijdDTO> updateWedstrijd(@Valid @RequestBody WedstrijdDTO wedstrijdDTO) throws URISyntaxException {
        log.debug("REST request to update Wedstrijd : {}", wedstrijdDTO);
        if (wedstrijdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WedstrijdDTO result = wedstrijdService.save(wedstrijdDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wedstrijdDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wedstrijds : get all the wedstrijds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wedstrijds in body
     */
    @GetMapping("/wedstrijds")
    @Timed
    public ResponseEntity<List<WedstrijdDTO>> getAllWedstrijds(Pageable pageable) {
        log.debug("REST request to get a page of Wedstrijds");
        Page<WedstrijdDTO> page = wedstrijdService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wedstrijds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wedstrijds/:id : get the "id" wedstrijd.
     *
     * @param id the id of the wedstrijdDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wedstrijdDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wedstrijds/{id}")
    @Timed
    public ResponseEntity<WedstrijdDTO> getWedstrijd(@PathVariable Long id) {
        log.debug("REST request to get Wedstrijd : {}", id);
        Optional<WedstrijdDTO> wedstrijdDTO = wedstrijdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wedstrijdDTO);
    }

    /**
     * DELETE  /wedstrijds/:id : delete the "id" wedstrijd.
     *
     * @param id the id of the wedstrijdDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wedstrijds/{id}")
    @Timed
    public ResponseEntity<Void> deleteWedstrijd(@PathVariable Long id) {
        log.debug("REST request to delete Wedstrijd : {}", id);
        wedstrijdService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
