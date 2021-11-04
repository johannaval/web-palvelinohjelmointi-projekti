package projekti;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import projekti.services.AccountService;
import projekti.services.FollowService;
import projekti.services.MessageCommentService;
import projekti.services.MessageLikeService;
import projekti.services.MessageService;
import projekti.services.PhotoService;
import projekti.services.ProfileService;

@Transactional
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class MockMvcTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private FollowService followService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageLikeService messageLikeService;

    @Autowired
    private MessageCommentService messageCommentService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper mapper;

    @Test
    public void loginAndRegistrationsReturnsStatusSuccessful() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().is2xxSuccessful());
        mockMvc.perform(get("/registrations")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void loginAndRegistrationsReturnsStatusOk() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().isOk());
        mockMvc.perform(get("/registrations")).andExpect(status().isOk());
        System.out.println("");
    }
}
