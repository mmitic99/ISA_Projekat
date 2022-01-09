package isa.FishingBookingApp.controller;

import com.google.gson.Gson;
import isa.FishingBookingApp.constants.ReservationEntitiesConstants;
import isa.FishingBookingApp.constants.UserConstants;
import isa.FishingBookingApp.dto.LoginUser;
import isa.FishingBookingApp.dto.UserTokenState;
import isa.FishingBookingApp.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static isa.FishingBookingApp.constants.ReservationConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {
    private static final String URL_PREFIX = "/api/reservation";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // Student 1
    @Test
    @Transactional
    @Rollback(true)
    public void testCheckingAvailableReservationEntities() throws Exception {
        String accessToken = getUserAccessToken();
        mockMvc.perform(get(URL_PREFIX + "/checkReservation")
                        .header("Authorization", accessToken)
                        .param("sort", "")
                        .param("types", "")
                        .param("search", "")
                        .param("date", DATE_FOR_RESERVATION_SEARCH)
                        .param("time", TIME_FOR_RESERVATION_SEARCH)
                        .param("days", DAYS_FOR_RESERVATION_SEARCH)
                        .param("guests", GUESTS_FOR_RESERVATION_SEARCH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(DB_NUMBER_OF_AVAILABLE_RESERVATION_ENTITIES)));
    }

    private String getUserAccessToken() throws Exception {
        LoginUser loginUser = new LoginUser();
        loginUser.setMailAddress(UserConstants.DB_USER_MAIL_ADDRESS);
        loginUser.setPassword(UserConstants.DB_USER_PASSWORD);

        String json = TestUtil.json(loginUser);

        MvcResult result = this.mockMvc.perform(post("/auth/login").contentType(contentType).content(json)).andReturn();
        result.getResponse().getContentAsString();

        Gson gson = new Gson();
        UserTokenState userTokenState = gson.fromJson(result.getResponse().getContentAsString(), UserTokenState.class);
        String accessToken = userTokenState.getAccessToken();
        accessToken = "Bearer " + accessToken;

        return accessToken;
    }
}
