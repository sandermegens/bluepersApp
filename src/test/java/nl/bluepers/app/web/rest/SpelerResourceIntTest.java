package nl.bluepers.app.web.rest;

import nl.bluepers.app.BluepersApp;

import nl.bluepers.app.domain.Speler;
import nl.bluepers.app.repository.SpelerRepository;
import nl.bluepers.app.service.SpelerService;
import nl.bluepers.app.service.dto.SpelerDTO;
import nl.bluepers.app.service.mapper.SpelerMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static nl.bluepers.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import nl.bluepers.app.domain.enumeration.Positie;
/**
 * Test class for the SpelerResource REST controller.
 *
 * @see SpelerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BluepersApp.class)
public class SpelerResourceIntTest {

    private static final String DEFAULT_VOORNAAM = "AAAAAAAAAA";
    private static final String UPDATED_VOORNAAM = "BBBBBBBBBB";

    private static final String DEFAULT_TUSSENVOEGSEL = "AAAAAAAAAA";
    private static final String UPDATED_TUSSENVOEGSEL = "BBBBBBBBBB";

    private static final String DEFAULT_ACHTERNAAM = "AAAAAAAAAA";
    private static final String UPDATED_ACHTERNAAM = "BBBBBBBBBB";

    private static final Integer DEFAULT_RUGNUMMER = 1;
    private static final Integer UPDATED_RUGNUMMER = 2;

    private static final Positie DEFAULT_POSITIE = Positie.KEEPER;
    private static final Positie UPDATED_POSITIE = Positie.LINKSBACK;

    private static final LocalDate DEFAULT_GEBOORTE_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GEBOORTE_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DEBUUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEBUUT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_BIJZONDERHEDEN = "AAAAAAAAAA";
    private static final String UPDATED_BIJZONDERHEDEN = "BBBBBBBBBB";

    @Autowired
    private SpelerRepository spelerRepository;


    @Autowired
    private SpelerMapper spelerMapper;
    

    @Autowired
    private SpelerService spelerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSpelerMockMvc;

    private Speler speler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpelerResource spelerResource = new SpelerResource(spelerService);
        this.restSpelerMockMvc = MockMvcBuilders.standaloneSetup(spelerResource)
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
    public static Speler createEntity(EntityManager em) {
        Speler speler = new Speler()
            .voornaam(DEFAULT_VOORNAAM)
            .tussenvoegsel(DEFAULT_TUSSENVOEGSEL)
            .achternaam(DEFAULT_ACHTERNAAM)
            .rugnummer(DEFAULT_RUGNUMMER)
            .positie(DEFAULT_POSITIE)
            .geboorteDatum(DEFAULT_GEBOORTE_DATUM)
            .debuut(DEFAULT_DEBUUT)
            .bijzonderheden(DEFAULT_BIJZONDERHEDEN);
        return speler;
    }

    @Before
    public void initTest() {
        speler = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpeler() throws Exception {
        int databaseSizeBeforeCreate = spelerRepository.findAll().size();

        // Create the Speler
        SpelerDTO spelerDTO = spelerMapper.toDto(speler);
        restSpelerMockMvc.perform(post("/api/spelers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spelerDTO)))
            .andExpect(status().isCreated());

        // Validate the Speler in the database
        List<Speler> spelerList = spelerRepository.findAll();
        assertThat(spelerList).hasSize(databaseSizeBeforeCreate + 1);
        Speler testSpeler = spelerList.get(spelerList.size() - 1);
        assertThat(testSpeler.getVoornaam()).isEqualTo(DEFAULT_VOORNAAM);
        assertThat(testSpeler.getTussenvoegsel()).isEqualTo(DEFAULT_TUSSENVOEGSEL);
        assertThat(testSpeler.getAchternaam()).isEqualTo(DEFAULT_ACHTERNAAM);
        assertThat(testSpeler.getRugnummer()).isEqualTo(DEFAULT_RUGNUMMER);
        assertThat(testSpeler.getPositie()).isEqualTo(DEFAULT_POSITIE);
        assertThat(testSpeler.getGeboorteDatum()).isEqualTo(DEFAULT_GEBOORTE_DATUM);
        assertThat(testSpeler.getDebuut()).isEqualTo(DEFAULT_DEBUUT);
        assertThat(testSpeler.getBijzonderheden()).isEqualTo(DEFAULT_BIJZONDERHEDEN);
    }

    @Test
    @Transactional
    public void createSpelerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = spelerRepository.findAll().size();

        // Create the Speler with an existing ID
        speler.setId(1L);
        SpelerDTO spelerDTO = spelerMapper.toDto(speler);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpelerMockMvc.perform(post("/api/spelers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spelerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Speler in the database
        List<Speler> spelerList = spelerRepository.findAll();
        assertThat(spelerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkGeboorteDatumIsRequired() throws Exception {
        int databaseSizeBeforeTest = spelerRepository.findAll().size();
        // set the field null
        speler.setGeboorteDatum(null);

        // Create the Speler, which fails.
        SpelerDTO spelerDTO = spelerMapper.toDto(speler);

        restSpelerMockMvc.perform(post("/api/spelers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spelerDTO)))
            .andExpect(status().isBadRequest());

        List<Speler> spelerList = spelerRepository.findAll();
        assertThat(spelerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpelers() throws Exception {
        // Initialize the database
        spelerRepository.saveAndFlush(speler);

        // Get all the spelerList
        restSpelerMockMvc.perform(get("/api/spelers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speler.getId().intValue())))
            .andExpect(jsonPath("$.[*].voornaam").value(hasItem(DEFAULT_VOORNAAM.toString())))
            .andExpect(jsonPath("$.[*].tussenvoegsel").value(hasItem(DEFAULT_TUSSENVOEGSEL.toString())))
            .andExpect(jsonPath("$.[*].achternaam").value(hasItem(DEFAULT_ACHTERNAAM.toString())))
            .andExpect(jsonPath("$.[*].rugnummer").value(hasItem(DEFAULT_RUGNUMMER)))
            .andExpect(jsonPath("$.[*].positie").value(hasItem(DEFAULT_POSITIE.toString())))
            .andExpect(jsonPath("$.[*].geboorteDatum").value(hasItem(DEFAULT_GEBOORTE_DATUM.toString())))
            .andExpect(jsonPath("$.[*].debuut").value(hasItem(DEFAULT_DEBUUT.toString())))
            .andExpect(jsonPath("$.[*].bijzonderheden").value(hasItem(DEFAULT_BIJZONDERHEDEN.toString())));
    }
    

    @Test
    @Transactional
    public void getSpeler() throws Exception {
        // Initialize the database
        spelerRepository.saveAndFlush(speler);

        // Get the speler
        restSpelerMockMvc.perform(get("/api/spelers/{id}", speler.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(speler.getId().intValue()))
            .andExpect(jsonPath("$.voornaam").value(DEFAULT_VOORNAAM.toString()))
            .andExpect(jsonPath("$.tussenvoegsel").value(DEFAULT_TUSSENVOEGSEL.toString()))
            .andExpect(jsonPath("$.achternaam").value(DEFAULT_ACHTERNAAM.toString()))
            .andExpect(jsonPath("$.rugnummer").value(DEFAULT_RUGNUMMER))
            .andExpect(jsonPath("$.positie").value(DEFAULT_POSITIE.toString()))
            .andExpect(jsonPath("$.geboorteDatum").value(DEFAULT_GEBOORTE_DATUM.toString()))
            .andExpect(jsonPath("$.debuut").value(DEFAULT_DEBUUT.toString()))
            .andExpect(jsonPath("$.bijzonderheden").value(DEFAULT_BIJZONDERHEDEN.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSpeler() throws Exception {
        // Get the speler
        restSpelerMockMvc.perform(get("/api/spelers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpeler() throws Exception {
        // Initialize the database
        spelerRepository.saveAndFlush(speler);

        int databaseSizeBeforeUpdate = spelerRepository.findAll().size();

        // Update the speler
        Speler updatedSpeler = spelerRepository.findById(speler.getId()).get();
        // Disconnect from session so that the updates on updatedSpeler are not directly saved in db
        em.detach(updatedSpeler);
        updatedSpeler
            .voornaam(UPDATED_VOORNAAM)
            .tussenvoegsel(UPDATED_TUSSENVOEGSEL)
            .achternaam(UPDATED_ACHTERNAAM)
            .rugnummer(UPDATED_RUGNUMMER)
            .positie(UPDATED_POSITIE)
            .geboorteDatum(UPDATED_GEBOORTE_DATUM)
            .debuut(UPDATED_DEBUUT)
            .bijzonderheden(UPDATED_BIJZONDERHEDEN);
        SpelerDTO spelerDTO = spelerMapper.toDto(updatedSpeler);

        restSpelerMockMvc.perform(put("/api/spelers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spelerDTO)))
            .andExpect(status().isOk());

        // Validate the Speler in the database
        List<Speler> spelerList = spelerRepository.findAll();
        assertThat(spelerList).hasSize(databaseSizeBeforeUpdate);
        Speler testSpeler = spelerList.get(spelerList.size() - 1);
        assertThat(testSpeler.getVoornaam()).isEqualTo(UPDATED_VOORNAAM);
        assertThat(testSpeler.getTussenvoegsel()).isEqualTo(UPDATED_TUSSENVOEGSEL);
        assertThat(testSpeler.getAchternaam()).isEqualTo(UPDATED_ACHTERNAAM);
        assertThat(testSpeler.getRugnummer()).isEqualTo(UPDATED_RUGNUMMER);
        assertThat(testSpeler.getPositie()).isEqualTo(UPDATED_POSITIE);
        assertThat(testSpeler.getGeboorteDatum()).isEqualTo(UPDATED_GEBOORTE_DATUM);
        assertThat(testSpeler.getDebuut()).isEqualTo(UPDATED_DEBUUT);
        assertThat(testSpeler.getBijzonderheden()).isEqualTo(UPDATED_BIJZONDERHEDEN);
    }

    @Test
    @Transactional
    public void updateNonExistingSpeler() throws Exception {
        int databaseSizeBeforeUpdate = spelerRepository.findAll().size();

        // Create the Speler
        SpelerDTO spelerDTO = spelerMapper.toDto(speler);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSpelerMockMvc.perform(put("/api/spelers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spelerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Speler in the database
        List<Speler> spelerList = spelerRepository.findAll();
        assertThat(spelerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpeler() throws Exception {
        // Initialize the database
        spelerRepository.saveAndFlush(speler);

        int databaseSizeBeforeDelete = spelerRepository.findAll().size();

        // Get the speler
        restSpelerMockMvc.perform(delete("/api/spelers/{id}", speler.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Speler> spelerList = spelerRepository.findAll();
        assertThat(spelerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Speler.class);
        Speler speler1 = new Speler();
        speler1.setId(1L);
        Speler speler2 = new Speler();
        speler2.setId(speler1.getId());
        assertThat(speler1).isEqualTo(speler2);
        speler2.setId(2L);
        assertThat(speler1).isNotEqualTo(speler2);
        speler1.setId(null);
        assertThat(speler1).isNotEqualTo(speler2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpelerDTO.class);
        SpelerDTO spelerDTO1 = new SpelerDTO();
        spelerDTO1.setId(1L);
        SpelerDTO spelerDTO2 = new SpelerDTO();
        assertThat(spelerDTO1).isNotEqualTo(spelerDTO2);
        spelerDTO2.setId(spelerDTO1.getId());
        assertThat(spelerDTO1).isEqualTo(spelerDTO2);
        spelerDTO2.setId(2L);
        assertThat(spelerDTO1).isNotEqualTo(spelerDTO2);
        spelerDTO1.setId(null);
        assertThat(spelerDTO1).isNotEqualTo(spelerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(spelerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(spelerMapper.fromId(null)).isNull();
    }
}
