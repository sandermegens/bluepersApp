package nl.bluepers.app.web.rest;

import nl.bluepers.app.BluepersApp;

import nl.bluepers.app.domain.Wedstrijd;
import nl.bluepers.app.repository.WedstrijdRepository;
import nl.bluepers.app.service.WedstrijdService;
import nl.bluepers.app.service.dto.WedstrijdDTO;
import nl.bluepers.app.service.mapper.WedstrijdMapper;
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

/**
 * Test class for the WedstrijdResource REST controller.
 *
 * @see WedstrijdResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BluepersApp.class)
public class WedstrijdResourceIntTest {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TIJD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIJD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PLAATS = "AAAAAAAAAA";
    private static final String UPDATED_PLAATS = "BBBBBBBBBB";

    @Autowired
    private WedstrijdRepository wedstrijdRepository;


    @Autowired
    private WedstrijdMapper wedstrijdMapper;
    

    @Autowired
    private WedstrijdService wedstrijdService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWedstrijdMockMvc;

    private Wedstrijd wedstrijd;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WedstrijdResource wedstrijdResource = new WedstrijdResource(wedstrijdService);
        this.restWedstrijdMockMvc = MockMvcBuilders.standaloneSetup(wedstrijdResource)
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
    public static Wedstrijd createEntity(EntityManager em) {
        Wedstrijd wedstrijd = new Wedstrijd()
            .datum(DEFAULT_DATUM)
            .tijd(DEFAULT_TIJD)
            .plaats(DEFAULT_PLAATS);
        return wedstrijd;
    }

    @Before
    public void initTest() {
        wedstrijd = createEntity(em);
    }

    @Test
    @Transactional
    public void createWedstrijd() throws Exception {
        int databaseSizeBeforeCreate = wedstrijdRepository.findAll().size();

        // Create the Wedstrijd
        WedstrijdDTO wedstrijdDTO = wedstrijdMapper.toDto(wedstrijd);
        restWedstrijdMockMvc.perform(post("/api/wedstrijds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wedstrijdDTO)))
            .andExpect(status().isCreated());

        // Validate the Wedstrijd in the database
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeCreate + 1);
        Wedstrijd testWedstrijd = wedstrijdList.get(wedstrijdList.size() - 1);
        assertThat(testWedstrijd.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testWedstrijd.getTijd()).isEqualTo(DEFAULT_TIJD);
        assertThat(testWedstrijd.getPlaats()).isEqualTo(DEFAULT_PLAATS);
    }

    @Test
    @Transactional
    public void createWedstrijdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wedstrijdRepository.findAll().size();

        // Create the Wedstrijd with an existing ID
        wedstrijd.setId(1L);
        WedstrijdDTO wedstrijdDTO = wedstrijdMapper.toDto(wedstrijd);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWedstrijdMockMvc.perform(post("/api/wedstrijds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wedstrijdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wedstrijd in the database
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDatumIsRequired() throws Exception {
        int databaseSizeBeforeTest = wedstrijdRepository.findAll().size();
        // set the field null
        wedstrijd.setDatum(null);

        // Create the Wedstrijd, which fails.
        WedstrijdDTO wedstrijdDTO = wedstrijdMapper.toDto(wedstrijd);

        restWedstrijdMockMvc.perform(post("/api/wedstrijds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wedstrijdDTO)))
            .andExpect(status().isBadRequest());

        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTijdIsRequired() throws Exception {
        int databaseSizeBeforeTest = wedstrijdRepository.findAll().size();
        // set the field null
        wedstrijd.setTijd(null);

        // Create the Wedstrijd, which fails.
        WedstrijdDTO wedstrijdDTO = wedstrijdMapper.toDto(wedstrijd);

        restWedstrijdMockMvc.perform(post("/api/wedstrijds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wedstrijdDTO)))
            .andExpect(status().isBadRequest());

        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWedstrijds() throws Exception {
        // Initialize the database
        wedstrijdRepository.saveAndFlush(wedstrijd);

        // Get all the wedstrijdList
        restWedstrijdMockMvc.perform(get("/api/wedstrijds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wedstrijd.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].tijd").value(hasItem(DEFAULT_TIJD.toString())))
            .andExpect(jsonPath("$.[*].plaats").value(hasItem(DEFAULT_PLAATS.toString())));
    }
    

    @Test
    @Transactional
    public void getWedstrijd() throws Exception {
        // Initialize the database
        wedstrijdRepository.saveAndFlush(wedstrijd);

        // Get the wedstrijd
        restWedstrijdMockMvc.perform(get("/api/wedstrijds/{id}", wedstrijd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wedstrijd.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.tijd").value(DEFAULT_TIJD.toString()))
            .andExpect(jsonPath("$.plaats").value(DEFAULT_PLAATS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingWedstrijd() throws Exception {
        // Get the wedstrijd
        restWedstrijdMockMvc.perform(get("/api/wedstrijds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWedstrijd() throws Exception {
        // Initialize the database
        wedstrijdRepository.saveAndFlush(wedstrijd);

        int databaseSizeBeforeUpdate = wedstrijdRepository.findAll().size();

        // Update the wedstrijd
        Wedstrijd updatedWedstrijd = wedstrijdRepository.findById(wedstrijd.getId()).get();
        // Disconnect from session so that the updates on updatedWedstrijd are not directly saved in db
        em.detach(updatedWedstrijd);
        updatedWedstrijd
            .datum(UPDATED_DATUM)
            .tijd(UPDATED_TIJD)
            .plaats(UPDATED_PLAATS);
        WedstrijdDTO wedstrijdDTO = wedstrijdMapper.toDto(updatedWedstrijd);

        restWedstrijdMockMvc.perform(put("/api/wedstrijds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wedstrijdDTO)))
            .andExpect(status().isOk());

        // Validate the Wedstrijd in the database
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeUpdate);
        Wedstrijd testWedstrijd = wedstrijdList.get(wedstrijdList.size() - 1);
        assertThat(testWedstrijd.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testWedstrijd.getTijd()).isEqualTo(UPDATED_TIJD);
        assertThat(testWedstrijd.getPlaats()).isEqualTo(UPDATED_PLAATS);
    }

    @Test
    @Transactional
    public void updateNonExistingWedstrijd() throws Exception {
        int databaseSizeBeforeUpdate = wedstrijdRepository.findAll().size();

        // Create the Wedstrijd
        WedstrijdDTO wedstrijdDTO = wedstrijdMapper.toDto(wedstrijd);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWedstrijdMockMvc.perform(put("/api/wedstrijds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wedstrijdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wedstrijd in the database
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWedstrijd() throws Exception {
        // Initialize the database
        wedstrijdRepository.saveAndFlush(wedstrijd);

        int databaseSizeBeforeDelete = wedstrijdRepository.findAll().size();

        // Get the wedstrijd
        restWedstrijdMockMvc.perform(delete("/api/wedstrijds/{id}", wedstrijd.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Wedstrijd> wedstrijdList = wedstrijdRepository.findAll();
        assertThat(wedstrijdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wedstrijd.class);
        Wedstrijd wedstrijd1 = new Wedstrijd();
        wedstrijd1.setId(1L);
        Wedstrijd wedstrijd2 = new Wedstrijd();
        wedstrijd2.setId(wedstrijd1.getId());
        assertThat(wedstrijd1).isEqualTo(wedstrijd2);
        wedstrijd2.setId(2L);
        assertThat(wedstrijd1).isNotEqualTo(wedstrijd2);
        wedstrijd1.setId(null);
        assertThat(wedstrijd1).isNotEqualTo(wedstrijd2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WedstrijdDTO.class);
        WedstrijdDTO wedstrijdDTO1 = new WedstrijdDTO();
        wedstrijdDTO1.setId(1L);
        WedstrijdDTO wedstrijdDTO2 = new WedstrijdDTO();
        assertThat(wedstrijdDTO1).isNotEqualTo(wedstrijdDTO2);
        wedstrijdDTO2.setId(wedstrijdDTO1.getId());
        assertThat(wedstrijdDTO1).isEqualTo(wedstrijdDTO2);
        wedstrijdDTO2.setId(2L);
        assertThat(wedstrijdDTO1).isNotEqualTo(wedstrijdDTO2);
        wedstrijdDTO1.setId(null);
        assertThat(wedstrijdDTO1).isNotEqualTo(wedstrijdDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wedstrijdMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wedstrijdMapper.fromId(null)).isNull();
    }
}
