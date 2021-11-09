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

    @GetMapping("/index")
    public String getMessages(@ModelAttribute Message message, Model model) {

        Profile currentUser = profileService.findByProfileName(accountService.getCurrentUser().getProfileName());
        Photo profilePhoto = photoService.getProfilePhoto(currentUser);

        if (profilePhoto != null) {
            model.addAttribute("profilePhoto", profilePhoto.getNumber());
        }

        List<Follow> followedProfiles = followService.findByFollower(currentUser);
        List<Profile> profiles = new ArrayList<>();

        profiles.add(currentUser);

        for (Follow follow : followedProfiles) {
            profiles.add(follow.getFollowing());
        }

        List<Message> messages = messageService.getMessagesByProfiles(profiles);

        for (Message msg : messages) {

            List<MessageLike> likes = messageLikeService.getMessageLikesByMessage(msg);
            Boolean liked = false;

            for (MessageLike like : likes) {
                if (like.getProfile().equals(currentUser)) {
                    liked = true;
                }
            }
            msg.setLiked(liked);
            messageService.save(msg);
        }

        List<Message> msgs = messageService.getMessagesByProfiles(profiles);

        if (msgs.size() > 0) {
            Long firstId = msgs.get(0).getId();
            return "redirect:/index/" + firstId;

        } else {
            //        model.addAttribute("messages", messages);
            model.addAttribute("currentUser", currentUser);
            return "index";
        }
    }

    @GetMapping("/index/{id}")
    public String getMessage(@PathVariable Long id, @ModelAttribute Message message, Model model) {

        Profile currentUser = profileService.findByProfileName(accountService.getCurrentUser().getProfileName());
        Photo profilePhoto = photoService.getProfilePhoto(currentUser);

        if (profilePhoto != null) {
            model.addAttribute("profilePhoto", profilePhoto.getNumber());
        }

        List<Follow> followedProfiles = followService.findByFollower(currentUser);
        List<Profile> profiles = new ArrayList<>();
        profiles.add(currentUser);

        for (Follow follow : followedProfiles) {
            profiles.add(follow.getFollowing());
        }

        message = messageService.findMessageById(id);
        Profile profile = message.getProfile();

        List<MessageLike> likes = messageLikeService.getMessageLikesByMessage(message);
        List<MessageComment> comments = messageCommentService.getMessageCommentsByMessage(message);

        for (MessageLike like : likes) {
            if (like.getProfile().equals(currentUser)) {
                message.setLiked(true);
                messageService.save(message);
            }
        }
        model.addAttribute("messages", messageService.getMessagesByProfiles(profiles));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("likeCount", likes.size());
        model.addAttribute("comments", comments);

        return "index";
    }

    @PostMapping("/index")
    public String postMessage(@Valid @ModelAttribute Message message, BindingResult bindingResult, Model model) {

        Profile currentUser = profileService.findByProfileName(accountService.getCurrentUser().getProfileName());

        if (!bindingResult.hasErrors()) {

            message.setContent(message.getContent());
            message.setProfile(currentUser);
            message.setLikeCount(0);

            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            message.setDateString(date.format(formatter));

            messageService.save(message);

            return "redirect:/index/" + message.getId();
        }
        return "redirect:/index";
    }

    @PostMapping("/index/{id}/like")
    public String addLike(@PathVariable Long id) {

        Profile currentUserProfile = profileService.findByProfileName(accountService.getCurrentUser().getProfileName());

        Message message = messageService.findMessageById(id);
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

        Profile currentUserProfile = profileService.findByProfileName(accountService.getCurrentUser().getProfileName());

        Message message = messageService.findMessageById(id);
        List<MessageLike> likes = messageLikeService.getMessageLikesByMessage(message);

        for (MessageLike like : likes) {
            if (like.getProfile().getProfileName().equals(currentUserProfile.getProfileName())) {
                messageLikeService.deleteLike(like);
                message.setLikeCount(message.getLikeCount() - 1);
                messageService.save(message);
            }
        }
        return "redirect:/index";
    }

    @PostMapping("/index/{id}/comment")
    public String addComment(Model model, @PathVariable Long id, String commentContent) {

        Profile currentUserProfile = profileService.findByProfileName(accountService.getCurrentUser().getProfileName());

        Message message = messageService.findMessageById(id);
        List<MessageComment> comments = messageCommentService.getMessageCommentsByMessage(message);

        MessageComment messageComment = new MessageComment();
        messageComment.setMessage(message);
        messageComment.setProfile(currentUserProfile);
        messageComment.setCommentContent(commentContent);

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        messageComment.setDateString(date.format(formatter));
        messageCommentService.save(messageComment);

        return "redirect:/index/" + id;
    }
}
