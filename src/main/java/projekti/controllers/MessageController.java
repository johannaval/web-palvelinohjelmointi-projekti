package projekti.controllers;

import java.util.ArrayList;
import java.util.List;
import static jdk.internal.joptsimple.internal.Messages.message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import projekti.entities.Account;
import projekti.entities.Follow;
import projekti.entities.Message;
import projekti.entities.MessageComment;
import projekti.entities.MessageLike;
import projekti.entities.Photo;
import projekti.entities.PhotoLike;
import projekti.entities.Profile;
import projekti.services.AccountService;
import projekti.services.FollowService;
import projekti.services.MessageCommentService;
import projekti.services.MessageLikeService;
import projekti.services.MessageService;
import projekti.services.PhotoService;
import projekti.services.ProfileService;

@Controller
public class MessageController {

    @Autowired
    ProfileService profileService;

    @Autowired
    MessageService messageService;

    @Autowired
    MessageLikeService messageLikeService;

    @Autowired
    MessageCommentService messageCommentService;

    @Autowired
    AccountService accountService;

    @Autowired
    FollowService followService;

    @Autowired
    PhotoService photoService;

    @PostMapping("/index")
    public String postMessage(@RequestParam String content) {

        Profile currentUser = profileService.findByProfileName(accountService.getCurrentUser().getProfileName());

        Message message = new Message();
        message.setContent(content);
        message.setProfile(currentUser);
        message.setLikeCount(0);

        messageService.save(message);

        return "redirect:/index";

    }

    @GetMapping("/index")
    public String getMessages(Model model) {

        Profile currentUser = profileService.findByProfileName(accountService.getCurrentUser().getProfileName());
        List<Follow> followedProfiles = followService.findByFollower(currentUser);
        List<Profile> profiles = new ArrayList<>();

        profiles.add(currentUser);

        Photo profilePhoto = photoService.getProfilePhoto(currentUser);

        if (profilePhoto != null) {
            model.addAttribute("profilePhoto", profilePhoto.getNumber());
        }

        for (Follow follow : followedProfiles) {
            profiles.add(follow.getFollowing());
        }

        model.addAttribute("messages", messageService.getMessagesByProfiles(profiles));
        model.addAttribute("currentUser", currentUser);

        List<Message> m = messageService.getMessagesByProfiles(profiles);

        if (m.size() > 0) {
            Long id = m.get(0).getId();
            return "redirect:/index/" + id;
        } else {
            return "index";
        }
    }

    @GetMapping("/index/{id}")
    public String getMessage(@PathVariable Long id, Model model) {

        Profile currentUser = profileService.findByProfileName(accountService.getCurrentUser().getProfileName());
        List<Follow> followedProfiles = followService.findByFollower(currentUser);
        List<Profile> profiles = new ArrayList<>();

        profiles.add(currentUser);

        Photo profilePhoto = photoService.getProfilePhoto(currentUser);

        if (profilePhoto != null) {
            model.addAttribute("profilePhoto", profilePhoto.getNumber());
        }

        for (Follow follow : followedProfiles) {
            profiles.add(follow.getFollowing());
        }

        model.addAttribute("messages", messageService.getMessagesByProfiles(profiles));
        model.addAttribute("currentUser", currentUser);

        Message message = messageService.findMessageById(id);
        Profile profile = message.getProfile();

        List<MessageLike> likes = messageLikeService.getMessageLikesByMessage(message);

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(likes.size());

        List<MessageComment> comments = messageCommentService.getMessageCommentsByMessage(message);

        model.addAttribute("likeCount", likes.size());
        model.addAttribute("comments", comments);

        return "index";
    }

    @PostMapping("/index/{id}/like")
    public String addLike(@PathVariable Long id) {

        Message message = messageService.findMessageById(id);

        Account currentUser = accountService.getCurrentUser();
        Profile currentUserProfile = profileService.findByProfileName(currentUser.getProfileName());

        MessageLike messageLike = new MessageLike();
        messageLike.setMessage(message);
        messageLike.setProfile(currentUserProfile);
        messageLikeService.save(messageLike);

        List<MessageLike> likesCount = messageLikeService.getMessageLikesByMessage(message);
        message.setLikeCount(likesCount.size());
        messageService.save(message);

        return "redirect:/index/" + id;

    }
}
