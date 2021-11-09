package projekti.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.entities.Account;
import projekti.entities.Follow;
import projekti.entities.Message;
import projekti.entities.MessageComment;
import projekti.entities.MessageLike;
import projekti.entities.Photo;
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
    public String postMessage(@Valid @ModelAttribute Message message, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            
            return "redirect:/index";
        }

        Profile currentUser = profileService.findByProfileName(accountService.getCurrentUser().getProfileName());

        message.setContent(message.getContent());
        message.setProfile(currentUser);
        message.setLikeCount(0);

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        message.setDateString(date.format(formatter));

        messageService.save(message);

        return "redirect:/index";

    }

    @GetMapping("/index")
    public String getMessages(@ModelAttribute Message message, Model model) {

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

        List<Message> messages = messageService.getMessagesByProfiles(profiles);

        for (Message m : messages) {
            m.setLiked(false);
            messageService.save(m);
        }

        model.addAttribute("messages", messages);
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
    public String getMessage(@PathVariable Long id, @ModelAttribute Message message, Model model) {

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

        message = messageService.findMessageById(id);
        Profile profile = message.getProfile();

        List<MessageLike> likes = messageLikeService.getMessageLikesByMessage(message);
        List<MessageComment> comments = messageCommentService.getMessageCommentsByMessage(message);

        for (MessageLike like : likes) {
            if (like.getProfile().getProfileName().equals(currentUser.getProfileName())) {
                message.setLiked(true);
                messageService.save(message);
            }
        }

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

    @PostMapping("/index/{id}/delete_like")
    public String delete_like(@PathVariable Long id) {

        Message message = messageService.findMessageById(id);

        Account currentUser = accountService.getCurrentUser();
        Profile currentUserProfile = profileService.findByProfileName(currentUser.getProfileName());

        List<MessageLike> likes = messageLikeService.getMessageLikesByMessage(message);

        for (MessageLike like : likes) {
            if (like.getProfile().getProfileName().equals(currentUserProfile.getProfileName())) {
                messageLikeService.deleteLike(like);
                System.out.println("TYKKÄÄJÄ LÖYTYI, POISTETAAN!!!!!");
                message.setLikeCount(message.getLikeCount() - 1);
                messageService.save(message);
            }
        }

        return "redirect:/index/";
    }
}
