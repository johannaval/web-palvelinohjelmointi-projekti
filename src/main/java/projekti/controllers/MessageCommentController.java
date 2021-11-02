package projekti.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.entities.Account;
import projekti.entities.Follow;
import projekti.entities.Message;
import projekti.entities.MessageComment;
import projekti.entities.Photo;
import projekti.entities.PhotoComment;
import projekti.entities.Profile;
import projekti.services.AccountService;
import projekti.services.FollowService;
import projekti.services.MessageCommentService;
import projekti.services.MessageService;
import projekti.services.PhotoCommentService;
import projekti.services.PhotoService;
import projekti.services.ProfileService;

@Controller
public class MessageCommentController {

    @Autowired
    MessageService messageService;

    @Autowired
    ProfileService profileService;

    @Autowired
    MessageCommentService messageCommentService;

    @Autowired
    AccountService accountService;

    @Autowired
    PhotoService photoService;

    @Autowired
    FollowService followService;

    @PostMapping("/index/{id}/comment")
    public String addComment(Model model, @PathVariable Long id, String commentContent) {

        Message message = messageService.findMessageById(id);

        List<MessageComment> comments = messageCommentService.getMessageCommentsByMessage(message);

        Account currentUser = accountService.getCurrentUser();
        Profile currentUserProfile = profileService.findByProfileName(currentUser.getProfileName());

        MessageComment messageComment = new MessageComment();
        messageComment.setMessage(message);
        messageComment.setProfile(currentUserProfile);
        messageComment.setCommentContent(commentContent);

        messageCommentService.save(messageComment);

        // eli ongelma, nyt tulostuu KAIKKI kyseisen messagen kommentit, ei rajaa vain viimeiset 10, sekä tulostuu vääräs järkässä.
        // jos käytän järjestämistä eli ilman message.comments, vaan pelkkä comments, niin sillon ne viestit sekoittuu.
        // ei katota, onko jo tykänyt viestistä, voi tykätä monta kertaa vain.
        // kommentit voi olla tyhjiä
        return "redirect:/index/" + id;

    }
}
