package isa.FishingBookingApp.controller;

import static isa.FishingBookingApp.constants.ReservationEntitiesConstants.DB_NUMBER_OF_SEARCHED_RESERVATION_ENTITIES;
import static isa.FishingBookingApp.constants.ReservationEntitiesConstants.SEARCH_RESERVATION_ENTITIES;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import isa.FishingBookingApp.constants.ReservationEntitiesConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationEntitiesControllerTest {
    private static final String URL_PREFIX = "/api/reservationEntities";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // Student 2
    @Test
    @Transactional
    @Rollback(true)
    public void testGetAdditionalServices() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/additionalServices/" + ReservationEntitiesConstants.DB_ID_RESERVATION_ENTITY_ONE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(ReservationEntitiesConstants.DB_ADDITIONAL_SERVICES_COUNT_OF_ENTITY_ONE)));
    }

    // Student 1
    @Test
    @Transactional
    @Rollback(true)
    public void testSearchReservationEntities() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/searchFilterSort")
                        .param("sort", "")
                        .param("types", "")
                        .param("search", SEARCH_RESERVATION_ENTITIES))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(DB_NUMBER_OF_SEARCHED_RESERVATION_ENTITIES)));
    }
}
