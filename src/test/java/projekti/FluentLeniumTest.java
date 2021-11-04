package projekti;

import java.util.concurrent.TimeUnit;
import org.fluentlenium.core.annotation.PageUrl;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.support.FindBy;
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

    @Before
    public void addUsers() {

        goTo("http://localhost:" + port + "/registrations");
        find("#username").fill().with("tero");
        find("#name").fill().with("tero");
        find("#profileName").fill().with("tero");
        find("#password").fill().with("tero");
        find("form").first().submit();

        goTo("http://localhost:" + port + "/registrations");
        find("#username").fill().with("tiina");
        find("#name").fill().with("tiina");
        find("#profileName").fill().with("tiina");
        find("#password").fill().with("tiina");
        find("form").first().submit();
    }

    @Test
    public void userCanRegister() {

        goTo("http://localhost:" + port + "/registrations");

        assertTrue(pageSource().contains("Rekisteröidy"));
        find("#name").fill().with("Tero Testaaja");
        find("#username").fill().with("Terppa");
        find("#profileName").fill().with("tero123");
        find("#password").fill().with("teronsalis");
        find("form").first().submit();

        assertTrue(pageSource().contains("Kirjaudu sisään"));
    }

    @Test
    public void userCanNotRegisterWithTooShortValues() throws InterruptedException {

        goTo("http://localhost:" + port + "/registrations");

        assertTrue(pageSource().contains("Rekisteröidy"));
        find("#name").fill().with("t");
        find("#username").fill().with("t");
        find("#profileName").fill().with("t");
        find("#password").fill().with("t");
        find("form").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);
        
        assertTrue(pageSource().contains("Nimessä pitää olla 3-50 merkkiä"));
        assertTrue(pageSource().contains("Käyttäjänimessä pitää olla 3-50 merkkiä"));
        assertTrue(pageSource().contains("Profiilin nimessä pitää olla 3-50 merkkiä"));
    }

    @Test
    public void notRegisteredUserCanNotLogIn() {

        goTo("http://localhost:" + port + "/login");

        find("#username").fill().with("Tuija2");
        find("#password").fill().with("tuijansalis");
        find("form").first().submit();

        assertTrue(pageSource().contains("Kirjaudu sisään"));
    }

    @Test
    public void registeredUserCanLogIn() {

        goTo("http://localhost:" + port + "/login");

        find("#username").fill().with("tero");
        find("#password").fill().with("tero");
        find("form").first().submit();

        String nimi = "tero";
        assertTrue(pageSource().contains(nimi));
        assertTrue(pageSource().contains("hauskaa että olet täällä taas!"));
    }

    /*
    @Test
    public void userCanNotLogInWithWrongUsername() throws InterruptedException {

        goTo("http://localhost:" + port + "/login");

        find("#username").fill().with("tero2");
        find("#password").fill().with("tero");
        find("form").first().submit();

        TimeUnit.MILLISECONDS.sleep(200);
        assertTrue(pageSource().contains("Käyttäjänimi tai salasana on väärin"));
    }

    @Test
    public void userCanNotLogInWithWrongPassword() throws InterruptedException {

        goTo("http://localhost:" + port + "/login");

        find("#username").fill().with("tero");
        find("#password").fill().with("vääräsalasana");
        find("form").first().submit();

        TimeUnit.MILLISECONDS.sleep(200);
        assertTrue(pageSource().contains("Käyttäjänimi tai salasana on väärin"));
    }
     */
    @Test
    public void loggedInUserCanSendMessage() throws InterruptedException {

        goTo("http://localhost:" + port + "/login");

        find("#username").fill().with("tero");
        find("#password").fill().with("tero");
        find("form").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);
        
        assertTrue(pageSource().contains("hauskaa että olet täällä taas!"));
        TimeUnit.MILLISECONDS.sleep(100);

        find("#exampleFormControlTextarea1").fill().with("Tämä on viesti terolta");
        find("form").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);

        assertTrue(pageSource().contains("Tämä on viesti terolta"));
    }

    @Test
    public void loggedInUserCanLikeAndUnLikeSentMessage() throws InterruptedException {

        goTo("http://localhost:" + port + "/login");

        find("#username").fill().with("tiina");
        find("#password").fill().with("tiina");
        find("form").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);

        find("#exampleFormControlTextarea1").fill().with("Tämä on viesti tiinalta");
        find("form").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);

        find("#like").first().click();
        TimeUnit.MILLISECONDS.sleep(100);

        find("#unlike").first().click();
        TimeUnit.MILLISECONDS.sleep(100);

        //   assertTrue(pageSource().contains("Tykkäykset: 0"));
    }

    @Test
    public void loggedInUserCanSeeOwnProfile() throws InterruptedException {

        goTo("http://localhost:" + port + "/login");

        find("#username").fill().with("tero");
        find("#password").fill().with("tero");
        find("form").first().submit();

        find("#profile").first().click();
        TimeUnit.MILLISECONDS.sleep(100);

        assertTrue(pageSource().contains("Tervetuloa sinun profiiliin!"));
        //   assertTrue(pageSource().contains("Seuraajat: (0kpl)"));
        //   assertTrue(pageSource().contains("Tykkäykset: 0"));
    }

    @Test
    public void loggedInUserCanSeeOwnPhotoGallery() throws InterruptedException {

        goTo("http://localhost:" + port + "/login");

        find("#username").fill().with("tiina");
        find("#password").fill().with("tiina");
        find("form").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);

        find("#photoGallery").first().click();
        TimeUnit.MILLISECONDS.sleep(100);

        assertTrue(pageSource().contains("Lisää kuvia kuva-albumiisi"));
    }

    @Test
    public void loggedInUserCanLogOut() throws InterruptedException {

        goTo("http://localhost:" + port + "/login");

        find("#username").fill().with("tero");
        find("#password").fill().with("tero");
        find("form").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);

        find("#logout").first().click();
        TimeUnit.MILLISECONDS.sleep(100);

        assertTrue(pageSource().contains("Kirjaudu sisään"));

    }

    @Test
    public void loggedInUserCanSeachProfiles() throws InterruptedException {

        goTo("http://localhost:" + port + "/login");

        find("#username").fill().with("tiina");
        find("#password").fill().with("tiina");
        find("form").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);
        
        find("#findProfiles").first().click();
        TimeUnit.MILLISECONDS.sleep(100);

        assertTrue(pageSource().contains("Etsi käyttäjiä:"));

        find("#name").fill().with("tero");
        find("form").first().submit();

        assertTrue(pageSource().contains("tero"));

        find("#showProfile").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);

        assertTrue(pageSource().contains("Tervetuloa käyttäjän "));
        //     assertTrue(pageSource().contains("Tero"));

        //   assertTrue(pageSource().contains("Seuraajat: (0kpl)"));
        //   assertTrue(pageSource().contains("Tykkäykset: 0"));
    }

    @Test
    public void loggedInUserCanFollowOtherUser() throws InterruptedException {

        goTo("http://localhost:" + port + "/login");

        find("#username").fill().with("tiina");
        find("#password").fill().with("tiina");
        find("form").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);
        
        goTo("http://localhost:" + port + "/accounts/tero");
        TimeUnit.MILLISECONDS.sleep(100);

        assertTrue(pageSource().contains("Tervetuloa käyttäjän"));

        find("#follow").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);
        
        assertTrue(pageSource().contains("Nimi"));
        assertTrue(pageSource().contains("Seurattu"));

        find("#unfollow").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);
        
        assertFalse(pageSource().contains("Nimi"));
        assertFalse(pageSource().contains("Seurattu"));

        //   assertTrue(pageSource().contains("Päiväys"));
        //     assertTrue(pageSource().contains("Tero"));
        //   assertTrue(pageSource().contains("Seuraajat: (0kpl)"));
        //   assertTrue(pageSource().contains("Tykkäykset: 0"));
    }

    @Test
    public void loggedInUserCanCommentSentMessage() throws InterruptedException {

        goTo("http://localhost:" + port + "/login");

        find("#username").fill().with("tero");
        find("#password").fill().with("tero");
        find("form").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);

        find("#exampleFormControlTextarea1").fill().with("Tämä on viesti terolta");
        find("form").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);

        find("#exampleFormControlTextarea1").last().fill().with("Tämä on kommentti terolta");
        find("form").last().submit();
        TimeUnit.MILLISECONDS.sleep(100);

        assertTrue(pageSource().contains("Tämä on kommentti terolta"));
        assertTrue(pageSource().contains("Lähettäjä:"));
        assertTrue(pageSource().contains("Päiväys:"));
    }

    @Test
    public void loggedInUserCanSeeFollowedUsersMessages() throws InterruptedException {

        goTo("http://localhost:" + port + "/login");

        find("#username").fill().with("tiina");
        find("#password").fill().with("tiina");
        find("form").first().submit();

        find("#exampleFormControlTextarea1").fill().with("Tämä on viesti tiinalta");
        find("form").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);

        find("#logout").first().click();
        TimeUnit.MILLISECONDS.sleep(100);

        find("#username").fill().with("tero");
        find("#password").fill().with("tero");
        find("form").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);
        
        goTo("http://localhost:" + port + "/accounts/tiina");
        TimeUnit.MILLISECONDS.sleep(100);

        assertTrue(pageSource().contains("Tervetuloa käyttäjän"));
        find("#follow").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);

        goTo("http://localhost:" + port + "/index");
        assertTrue(pageSource().contains("Tämä on viesti tiinalta"));
    }

    @Test
    public void loggedInUserCanSeeOtherUsersPhotoGallery() throws InterruptedException {

        goTo("http://localhost:" + port + "/login");

        find("#username").fill().with("tiina");
        find("#password").fill().with("tiina");
        find("form").first().submit();
        TimeUnit.MILLISECONDS.sleep(100);

        goTo("http://localhost:" + port + "/accounts/tero");
        TimeUnit.MILLISECONDS.sleep(100);

        find("#linkToGallery").first().click();
        TimeUnit.MILLISECONDS.sleep(100);

        assertTrue(pageSource().contains("ei ole vielä lisännyt kuvia! :("));
    }
}
