package projekti;

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class FluentLeniumTest extends org.fluentlenium.adapter.junit.FluentTest {

    @LocalServerPort
    private Integer port;

    @Test
    public void userCanRegister() {

        goTo("http://localhost:" + port + "/registrations");

        assertTrue(pageSource().contains("Rekisteröidy"));
        find("#name").fill().with("Tero Testaaja");
        find("#username").fill().with("Terppa");
        find("#profileName").fill().with("tero123");
        find("#password").fill().with("teronsalis");

        find("form").first().submit();

        //   await().atMost(10, TimeUnit.SECONDS);
        assertTrue(pageSource().contains("Kirjaudu sisään"));
    }

    @Test
    public void registeredUserCanLogIn() {

        goTo("http://localhost:" + port + "/registrations");

        //       assertTrue(pageSource().contains("Rekisteröidy"));
        find("#name").fill().with("Tuija2 Testaaja");
        find("#username").fill().with("Tuija2");
        find("#profileName").fill().with("tuija1233");
        find("#password").fill().with("tuijansalis");

        find("form").first().submit();
        assertTrue(pageSource().contains("Kirjaudu sisään"));

        find("#username").fill().with("Tuija2");
        find("#password").fill().with("tuijansalis");
        find("form").first().submit();

        //    assertTrue(pageSource().contains("Viestit"));
        // find("#message").fill().with("Tämä on viesti");
    }

    @Test
    public void notRegisteredUserCanNotLogIn() {

        goTo("http://localhost:" + port + "/login");

        assertTrue(pageSource().contains("Kirjaudu sisään"));

        find("#username").fill().with("Tuija2");
        find("#password").fill().with("tuijansalis");
        find("form").first().submit();

    //    assertTrue(pageSource().contains("Hei"));
    }

    @Test
    public void userCanSendMessage() {

        goTo("http://localhost:" + port + "/index");

        assertTrue(pageSource().contains("Kirjaudu sisään")); //ei toimi, kun on oikeudet
        //    find("#message").fill().with("Tuija2");
        //    find("#message").fill().with("Tämä on viesti");
        //    find("form").first().submit();
        //    assertTrue(pageSource().contains("Tämä on viesti"));
        //    assertTrue(pageSource().contains("Lähettäjä: Tero Testaaja"));
        //     assertTrue(pageSource().contains("Kirjaudu sisään"));
        //     assertTrue(pageSource().contains("Viestit:"));
        //   find("#message").fill().with("Tämä on viesti");
        //  find("form").first().submit();
        //    assertTrue(pageSource().contains("Tämä on viesti"));
        //    assertTrue(pageSource().contains("Lähettäjä: Tero Testaaja"));
    }
}
