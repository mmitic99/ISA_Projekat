package isa.FishingBookingApp.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

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

@RunWith(SpringRunner.class)
@SpringBootTest
public class CottageControllerTest {

    private static final String URL_PREFIX = "/api/cottage";

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
    public void testCreateCottage() throws Exception {
        String jsonString = "{\n" +
                "            \"promotionalDescription\":\"Vikendica na vrhu planine\",\n" +
                "                \"name\":\"" + ReservationEntitiesConstants.NEW_COTTAGE_NAME + "\",\n" +
                "                \"cottageOwnerId\":\"4\",\n" +
                "                \"cottageOwnerUsername\":\"isaproject.tim27+3@gmail.com\",\n" +
                "                \"price\":\"1500\",\n" +
                "                \"bedsPerRoom\":\"2\",\n" +
                "                \"numberOfRooms\":\"2\",\n" +
                "                \"street\":\"Glavna\",\n" +
                "                \"number\":\"123\",\n" +
                "                \"city\":\"Beograd\",\n" +
                "                \"postalCode\":\"1253\",\n" +
                "                \"country\":\"Srbija\"\n" +
                "        }";


        String accressToken = getUserAccressToken();
        this.mockMvc.perform(post(URL_PREFIX + "/create").contentType(contentType).content(jsonString).header("Authorization", accressToken))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.name").value(ReservationEntitiesConstants.NEW_COTTAGE_NAME));
    }

    //Student 2
    @Test
    @Transactional
    @Rollback(true)
    public void testGetCottagesOfUser() throws Exception {
        String accressToken = getUserAccressToken();
        this.mockMvc.perform(get(URL_PREFIX + "/" + UserConstants.DB_COTTAGE_OWNER_MAIL_ADDRESS + "/allCottages").contentType(contentType).header("Authorization", accressToken))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(ReservationEntitiesConstants.DB_COTTAGES_OF_OWNER)));
    }

    private String getUserAccressToken() throws Exception {
        LoginUser loginUser = new LoginUser();
        loginUser.setMailAddress(UserConstants.DB_COTTAGE_OWNER_MAIL_ADDRESS);
        loginUser.setPassword(UserConstants.DB_COTTAGE_OWNER_PASSWORD);

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
