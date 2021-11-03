package projekti;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static javax.swing.UIManager.get;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
import org.springframework.test.web.servlet.RequestBuilder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import projekti.services.AccountService;
import projekti.services.FollowService;
import projekti.services.MessageCommentService;
import projekti.services.MessageLikeService;
import projekti.services.MessageService;
import projekti.services.PhotoService;
import projekti.services.ProfileService;

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

    /*   @Autowired
    private WebApplicationContext context;

    private ObjectMapper mapper; */
    private ObjectMapper mapper;

    @Before
    public void createUsersAndMessages() {

        mapper = new ObjectMapper();
        //   mapper = new ObjectMapper();
        //   mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void modelHasAttributeMessages() throws Exception {
        mockMvc.perform((RequestBuilder) get("/index"))
                .andExpect(model().attributeExists("messages"));
    }

    @Test
    public void statusOk() throws Exception {
        mockMvc.perform((RequestBuilder) get("/index"))
                .andExpect(status().isOk());
    }

    @Test
    public void responseContainsTextAwesome() throws Exception {
        MvcResult res = mockMvc.perform((RequestBuilder) get("/messages"))
                .andReturn();

        String content = res.getResponse().getContentAsString();
        Assert.assertTrue(content.contains("Awesome"));
    }
}
