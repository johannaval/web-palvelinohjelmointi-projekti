package projekti.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.entities.Account;
import projekti.entities.Photo;
import projekti.entities.PhotoComment;
import projekti.entities.Profile;
import projekti.services.AccountService;
import projekti.services.PhotoCommentService;
import projekti.services.PhotoService;
import projekti.services.ProfileService;

@Controller
public class PhotoCommentController {

    @Autowired
    PhotoService photoService;

    @Autowired
    ProfileService profileService;

    @Autowired
    PhotoCommentService photoCommentService;

    @Autowired
    AccountService accountService;

    @PostMapping("/accounts/{profileName}/photos/{number}/comment")
    public String addComment(@PathVariable String profileName, @PathVariable Integer number, String comment) {

        Profile profile = profileService.findByProfileName(profileName);
        Photo photo = photoService.getPhotoFromProfile(profileService.findByProfileName(profileName), number);

        List<PhotoComment> comments = photoCommentService.getPhotoCommentsByPhoto(photo);

        Account currentUser = accountService.getCurrentUser();
        Profile currentUserProfile = profileService.findByProfileName(currentUser.getProfileName());

        PhotoComment photoComment = new PhotoComment();
        photoComment.setPhoto(photo);
        photoComment.setProfile(currentUserProfile);
        photoComment.setContent(comment);

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        photoComment.setDateString(date.format(formatter));

        photoCommentService.save(photoComment);

        return "redirect:/accounts/" + profileName + "/photos/" + number;

    }

}
