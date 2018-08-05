package nl.bluepers.app.web.rest;

import nl.bluepers.app.BluepersApp;

import nl.bluepers.app.domain.Competitie;
import nl.bluepers.app.repository.CompetitieRepository;
import nl.bluepers.app.service.CompetitieService;
import nl.bluepers.app.service.dto.CompetitieDTO;
import nl.bluepers.app.service.mapper.CompetitieMapper;
import nl.bluepers.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static nl.bluepers.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CompetitieResource REST controller.
 *
 * @see CompetitieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BluepersApp.class)
public class CompetitieResourceIntTest {

    private static final String DEFAULT_SEIZOEN = "7148-3172";
    private static final String UPDATED_SEIZOEN = "5019-0833";

    @Autowired
    private CompetitieRepository competitieRepository;


    @Autowired
    private CompetitieMapper competitieMapper;
    

    @Autowired
    private CompetitieService competitieService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompetitieMockMvc;

    private Competitie competitie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompetitieResource competitieResource = new CompetitieResource(competitieService);
        this.restCompetitieMockMvc = MockMvcBuilders.standaloneSetup(competitieResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competitie createEntity(EntityManager em) {
        Competitie competitie = new Competitie()
            .seizoen(DEFAULT_SEIZOEN);
        return competitie;
    }

    @Before
    public void initTest() {
        competitie = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitie() throws Exception {
        int databaseSizeBeforeCreate = competitieRepository.findAll().size();

        // Create the Competitie
        CompetitieDTO competitieDTO = competitieMapper.toDto(competitie);
        restCompetitieMockMvc.perform(post("/api/competities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitieDTO)))
            .andExpect(status().isCreated());

        // Validate the Competitie in the database
        List<Competitie> competitieList = competitieRepository.findAll();
        assertThat(competitieList).hasSize(databaseSizeBeforeCreate + 1);
        Competitie testCompetitie = competitieList.get(competitieList.size() - 1);
        assertThat(testCompetitie.getSeizoen()).isEqualTo(DEFAULT_SEIZOEN);
    }

    @Test
    @Transactional
    public void createCompetitieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitieRepository.findAll().size();

        // Create the Competitie with an existing ID
        competitie.setId(1L);
        CompetitieDTO competitieDTO = competitieMapper.toDto(competitie);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitieMockMvc.perform(post("/api/competities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Competitie in the database
        List<Competitie> competitieList = competitieRepository.findAll();
        assertThat(competitieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSeizoenIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitieRepository.findAll().size();
        // set the field null
        competitie.setSeizoen(null);

        // Create the Competitie, which fails.
        CompetitieDTO competitieDTO = competitieMapper.toDto(competitie);

        restCompetitieMockMvc.perform(post("/api/competities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitieDTO)))
            .andExpect(status().isBadRequest());

        List<Competitie> competitieList = competitieRepository.findAll();
        assertThat(competitieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompetities() throws Exception {
        // Initialize the database
        competitieRepository.saveAndFlush(competitie);

        // Get all the competitieList
        restCompetitieMockMvc.perform(get("/api/competities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitie.getId().intValue())))
            .andExpect(jsonPath("$.[*].seizoen").value(hasItem(DEFAULT_SEIZOEN.toString())));
    }
    

    @Test
    @Transactional
    public void getCompetitie() throws Exception {
        // Initialize the database
        competitieRepository.saveAndFlush(competitie);

        // Get the competitie
        restCompetitieMockMvc.perform(get("/api/competities/{id}", competitie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competitie.getId().intValue()))
            .andExpect(jsonPath("$.seizoen").value(DEFAULT_SEIZOEN.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCompetitie() throws Exception {
        // Get the competitie
        restCompetitieMockMvc.perform(get("/api/competities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitie() throws Exception {
        // Initialize the database
        competitieRepository.saveAndFlush(competitie);

        int databaseSizeBeforeUpdate = competitieRepository.findAll().size();

        // Update the competitie
        Competitie updatedCompetitie = competitieRepository.findById(competitie.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitie are not directly saved in db
        em.detach(updatedCompetitie);
        updatedCompetitie
            .seizoen(UPDATED_SEIZOEN);
        CompetitieDTO competitieDTO = competitieMapper.toDto(updatedCompetitie);

        restCompetitieMockMvc.perform(put("/api/competities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitieDTO)))
            .andExpect(status().isOk());

        // Validate the Competitie in the database
        List<Competitie> competitieList = competitieRepository.findAll();
        assertThat(competitieList).hasSize(databaseSizeBeforeUpdate);
        Competitie testCompetitie = competitieList.get(competitieList.size() - 1);
        assertThat(testCompetitie.getSeizoen()).isEqualTo(UPDATED_SEIZOEN);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetitie() throws Exception {
        int databaseSizeBeforeUpdate = competitieRepository.findAll().size();

        // Create the Competitie
        CompetitieDTO competitieDTO = competitieMapper.toDto(competitie);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompetitieMockMvc.perform(put("/api/competities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Competitie in the database
        List<Competitie> competitieList = competitieRepository.findAll();
        assertThat(competitieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompetitie() throws Exception {
        // Initialize the database
        competitieRepository.saveAndFlush(competitie);

        int databaseSizeBeforeDelete = competitieRepository.findAll().size();

        // Get the competitie
        restCompetitieMockMvc.perform(delete("/api/competities/{id}", competitie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Competitie> competitieList = competitieRepository.findAll();
        assertThat(competitieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Competitie.class);
        Competitie competitie1 = new Competitie();
        competitie1.setId(1L);
        Competitie competitie2 = new Competitie();
        competitie2.setId(competitie1.getId());
        assertThat(competitie1).isEqualTo(competitie2);
        competitie2.setId(2L);
        assertThat(competitie1).isNotEqualTo(competitie2);
        competitie1.setId(null);
        assertThat(competitie1).isNotEqualTo(competitie2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitieDTO.class);
        CompetitieDTO competitieDTO1 = new CompetitieDTO();
        competitieDTO1.setId(1L);
        CompetitieDTO competitieDTO2 = new CompetitieDTO();
        assertThat(competitieDTO1).isNotEqualTo(competitieDTO2);
        competitieDTO2.setId(competitieDTO1.getId());
        assertThat(competitieDTO1).isEqualTo(competitieDTO2);
        competitieDTO2.setId(2L);
        assertThat(competitieDTO1).isNotEqualTo(competitieDTO2);
        competitieDTO1.setId(null);
        assertThat(competitieDTO1).isNotEqualTo(competitieDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(competitieMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(competitieMapper.fromId(null)).isNull();
    }
}
