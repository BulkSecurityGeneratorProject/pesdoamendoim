package com.pesdoamendoim.competition.web.rest;

import com.pesdoamendoim.competition.PeSdoAmendoimApp;

import com.pesdoamendoim.competition.domain.Round;
import com.pesdoamendoim.competition.repository.RoundRepository;
import com.pesdoamendoim.competition.service.RoundService;
import com.pesdoamendoim.competition.service.dto.RoundDTO;
import com.pesdoamendoim.competition.service.mapper.RoundMapper;
import com.pesdoamendoim.competition.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.pesdoamendoim.competition.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RoundResource REST controller.
 *
 * @see RoundResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PeSdoAmendoimApp.class)
public class RoundResourceIntTest {

    private static final String DEFAULT_ROUND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROUND_NAME = "BBBBBBBBBB";

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private RoundMapper roundMapper;

    @Autowired
    private RoundService roundService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRoundMockMvc;

    private Round round;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoundResource roundResource = new RoundResource(roundService);
        this.restRoundMockMvc = MockMvcBuilders.standaloneSetup(roundResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Round createEntity(EntityManager em) {
        Round round = new Round()
            .roundName(DEFAULT_ROUND_NAME);
        return round;
    }

    @Before
    public void initTest() {
        round = createEntity(em);
    }

    @Test
    @Transactional
    public void createRound() throws Exception {
        int databaseSizeBeforeCreate = roundRepository.findAll().size();

        // Create the Round
        RoundDTO roundDTO = roundMapper.toDto(round);
        restRoundMockMvc.perform(post("/api/rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roundDTO)))
            .andExpect(status().isCreated());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeCreate + 1);
        Round testRound = roundList.get(roundList.size() - 1);
        assertThat(testRound.getRoundName()).isEqualTo(DEFAULT_ROUND_NAME);
    }

    @Test
    @Transactional
    public void createRoundWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roundRepository.findAll().size();

        // Create the Round with an existing ID
        round.setId(1L);
        RoundDTO roundDTO = roundMapper.toDto(round);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoundMockMvc.perform(post("/api/rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roundDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRoundNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = roundRepository.findAll().size();
        // set the field null
        round.setRoundName(null);

        // Create the Round, which fails.
        RoundDTO roundDTO = roundMapper.toDto(round);

        restRoundMockMvc.perform(post("/api/rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roundDTO)))
            .andExpect(status().isBadRequest());

        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRounds() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);

        // Get all the roundList
        restRoundMockMvc.perform(get("/api/rounds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(round.getId().intValue())))
            .andExpect(jsonPath("$.[*].roundName").value(hasItem(DEFAULT_ROUND_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getRound() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);

        // Get the round
        restRoundMockMvc.perform(get("/api/rounds/{id}", round.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(round.getId().intValue()))
            .andExpect(jsonPath("$.roundName").value(DEFAULT_ROUND_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRound() throws Exception {
        // Get the round
        restRoundMockMvc.perform(get("/api/rounds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRound() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);

        int databaseSizeBeforeUpdate = roundRepository.findAll().size();

        // Update the round
        Round updatedRound = roundRepository.findById(round.getId()).get();
        // Disconnect from session so that the updates on updatedRound are not directly saved in db
        em.detach(updatedRound);
        updatedRound
            .roundName(UPDATED_ROUND_NAME);
        RoundDTO roundDTO = roundMapper.toDto(updatedRound);

        restRoundMockMvc.perform(put("/api/rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roundDTO)))
            .andExpect(status().isOk());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeUpdate);
        Round testRound = roundList.get(roundList.size() - 1);
        assertThat(testRound.getRoundName()).isEqualTo(UPDATED_ROUND_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRound() throws Exception {
        int databaseSizeBeforeUpdate = roundRepository.findAll().size();

        // Create the Round
        RoundDTO roundDTO = roundMapper.toDto(round);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoundMockMvc.perform(put("/api/rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roundDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRound() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);

        int databaseSizeBeforeDelete = roundRepository.findAll().size();

        // Delete the round
        restRoundMockMvc.perform(delete("/api/rounds/{id}", round.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Round.class);
        Round round1 = new Round();
        round1.setId(1L);
        Round round2 = new Round();
        round2.setId(round1.getId());
        assertThat(round1).isEqualTo(round2);
        round2.setId(2L);
        assertThat(round1).isNotEqualTo(round2);
        round1.setId(null);
        assertThat(round1).isNotEqualTo(round2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoundDTO.class);
        RoundDTO roundDTO1 = new RoundDTO();
        roundDTO1.setId(1L);
        RoundDTO roundDTO2 = new RoundDTO();
        assertThat(roundDTO1).isNotEqualTo(roundDTO2);
        roundDTO2.setId(roundDTO1.getId());
        assertThat(roundDTO1).isEqualTo(roundDTO2);
        roundDTO2.setId(2L);
        assertThat(roundDTO1).isNotEqualTo(roundDTO2);
        roundDTO1.setId(null);
        assertThat(roundDTO1).isNotEqualTo(roundDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(roundMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(roundMapper.fromId(null)).isNull();
    }
}
