package projekti;


import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import projekti.entities.Account;
import projekti.entities.Follow;
import projekti.entities.Message;
import projekti.entities.MessageComment;
import projekti.entities.MessageLike;
import projekti.entities.Profile;
import projekti.services.AccountService;
import projekti.services.FollowService;
import projekti.services.MessageCommentService;
import projekti.services.MessageLikeService;
import projekti.services.MessageService;
import projekti.services.PhotoService;
import projekti.services.ProfileService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class ProjektiTest {

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

    @Before
    public void addUsers() {

        Account account = new Account();
        account.setName("Turre");
        account.setUsername("Turre Testaaja");
        account.setProfileName("turre123");
        account.setPassword("abc123");

        accountService.saveAccount(account);
        profileService.saveProfile(account);

        Account other = new Account();
        other.setName("Tiina");
        other.setUsername("Tiina Testaaja");
        other.setProfileName("tiina123");
        other.setPassword("abc123");

        accountService.saveAccount(other);
        profileService.saveProfile(other);
    }

    @Test
    public void newUserCanBeAdded() {

        Account account = new Account();
        account.setName("Taina");
        account.setUsername("Taina Testaaja");
        account.setProfileName("taina123");
        account.setPassword("abc123");

        accountService.saveAccount(account);
        profileService.saveProfile(account);

        Profile profile = profileService.findByProfileName(account.getProfileName());

        assertEquals(3, accountService.getAllAccounts().size());
        assertEquals("taina123", profileService.findByProfileName(account.getProfileName()).getProfileName());
    }

    @Transactional
    @Test
    public void userCanFollowOtherUser() {

        Account account = accountService.getAccountByProfileName("turre123");
        Profile profile = profileService.findByProfileName(account.getProfileName());

        Account otherAccount = accountService.getAccountByProfileName("tiina123");
        Profile otherProfile = profileService.findByProfileName(otherAccount.getProfileName());

        Follow follow = new Follow();
        follow.setFollower(otherProfile);
        follow.setFollowing(profile);

        followService.save(follow);

        assertEquals("turre123", followService.getFollow(profile, otherProfile).getFollowing().getProfileName());
        assertEquals("tiina123", followService.getFollow(profile, otherProfile).getFollower().getProfileName());
    }

    @Transactional
    @Test
    public void userCanUnfollowOtherUser() {

        Account account = accountService.getAccountByProfileName("turre123");
        Profile profile = profileService.findByProfileName(account.getProfileName());

        Account otherAccount = accountService.getAccountByProfileName("tiina123");
        Profile otherProfile = profileService.findByProfileName(otherAccount.getProfileName());

        Follow follow = new Follow();
        follow.setFollower(otherProfile);
        follow.setFollowing(profile);

        followService.save(follow);
        assertEquals(follow, followService.getFollow(profile, otherProfile));

        followService.delete(follow);
        assertEquals(null, followService.getFollow(profile, otherProfile));
    }

    @Transactional
    @Test
    public void userCanAddMessage() {

        Account account = accountService.getAccountByProfileName("turre123");
        Profile profile = profileService.findByProfileName(account.getProfileName());

        List<Profile> profiles = new ArrayList<>();
        profiles.add(profile);

        assertEquals(0, messageService.getMessagesByProfiles(profiles).size());

        Message message = new Message();
        message.setContent("Hei!");
        message.setProfile(profile);
        messageService.save(message);

        assertEquals(1, messageService.getMessagesByProfiles(profiles).size());
        assertTrue(messageService.getMessagesByProfiles(profiles).get(0).equals(message));

    }

    @Transactional
    @Test
    public void userCanLikeMessage() {

        Account account = accountService.getAccountByProfileName("turre123");
        Profile profile = profileService.findByProfileName(account.getProfileName());

        Message message = new Message();
        message.setContent("Hei!");
        message.setProfile(profile);

        messageService.save(message);

        assertEquals(0, messageLikeService.getMessageLikesByMessage(message).size());
        MessageLike like = new MessageLike();
        like.setMessage(message);
        like.setProfile(profile);

        messageLikeService.save(like);
        assertEquals(1, messageLikeService.getMessageLikesByMessage(message).size());
        assertTrue(messageLikeService.getMessageLikesByMessage(message).contains(like));

    }

    @Transactional
    @Test
    public void userCanUnlikeMessage() {

        Account account = accountService.getAccountByProfileName("turre123");
        Profile profile = profileService.findByProfileName(account.getProfileName());

        Message message = new Message();
        message.setContent("Hei!");
        message.setProfile(profile);

        messageService.save(message);

        MessageLike like = new MessageLike();
        like.setMessage(message);
        like.setProfile(profile);

        messageLikeService.save(like);

        assertEquals(1, messageLikeService.getMessageLikesByMessage(message).size());

        messageLikeService.deleteLike(like);
        assertEquals(0, messageLikeService.getMessageLikesByMessage(message).size());
    }

    @Transactional
    @Test
    public void userCanAddCommentToMessage() {

        Account account = accountService.getAccountByProfileName("turre123");
        Profile profile = profileService.findByProfileName(account.getProfileName());

        Message message = new Message();
        message.setContent("Hei!");
        message.setProfile(profile);
        messageService.save(message);

        assertEquals(0, messageCommentService.getMessageCommentsByMessage(message).size());

        MessageComment comment = new MessageComment();
        comment.setMessage(message);
        comment.setProfile(profile);
        comment.setCommentContent("Hei hei!");

        messageCommentService.save(comment);

        List<MessageComment> comments = messageCommentService.getMessageCommentsByMessage(message);

        String messageCommentContent = comments.get(0).getCommentContent();

        assertEquals(1, messageCommentService.getMessageCommentsByMessage(message).size());
        assertTrue(messageCommentService.getMessageCommentsByMessage(message).get(0).equals(comment));
    }

    @Transactional
    @Test
    public void userCanSeeMessageFromFollowedUser() {

        Account account = accountService.getAccountByProfileName("turre123");
        Profile profile = profileService.findByProfileName(account.getProfileName());

        Account otherAccount = accountService.getAccountByProfileName("tiina123");
        Profile otherProfile = profileService.findByProfileName(otherAccount.getProfileName());

        Follow follow = new Follow();
        follow.setFollower(otherProfile);
        follow.setFollowing(profile);

        followService.save(follow);

        Message message = new Message();
        message.setContent("Terve! t.Turre");
        message.setProfile(profile);
        messageService.save(message);

        Message messageOther = new Message();
        messageOther.setContent("Terve! t.Tiina");
        messageOther.setProfile(otherProfile);
        messageService.save(messageOther);

        List<Profile> profiles = new ArrayList<>();
        profiles.add(otherProfile);
        List<Follow> followed_profiles = followService.findByFollower(otherProfile);

        for (Follow f : followed_profiles) {
            profiles.add(f.getFollowing());
        }
        List<Message> messages = messageService.getMessagesByProfiles(profiles);

        assertEquals("Terve! t.Tiina", messages.get(0).getContent());
        assertEquals("Terve! t.Turre", messages.get(1).getContent());
    }
}
