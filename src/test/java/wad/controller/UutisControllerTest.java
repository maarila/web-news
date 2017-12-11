package wad.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UutisControllerTest {

    @Autowired
    private WebApplicationContext webAppContext;

//    @Autowired
//    private UutisRepository uutisRepository;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void statusOk() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void kirjautumisenRedirectOikeinJosTunnuksetVaarin() throws Exception {
        mockMvc.perform(post("/kirjautuminen").param("kayttajatunnus", "hakkeri").param("salasana", "arvaus"))
                .andExpect(redirectedUrl("/kirjautuminen"));
    }
}
