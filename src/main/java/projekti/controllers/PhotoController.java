package projekti.controllers;

import java.io.IOException;
import java.util.List;
import javax.tools.FileObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import projekti.entities.Account;
import projekti.entities.Photo;
import projekti.entities.PhotoComment;
import projekti.entities.PhotoLike;
import projekti.entities.Profile;
import projekti.services.AccountService;
import projekti.services.PhotoCommentService;
import projekti.services.PhotoLikeService;
import projekti.services.PhotoService;
import projekti.services.ProfileService;

@Controller
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @Autowired
    PhotoLikeService photoLikeService;

    @Autowired
    ProfileService profileService;

    @Autowired
    PhotoCommentService photoCommentService;

    @Autowired
    AccountService accountService;

    @GetMapping("/accounts/{profileName}/photos")
    public String view(@PathVariable String profileName, Model model) {

        model.addAttribute("profile", profileService.getProfileByProfileName(profileName));
        model.addAttribute("currentUser", accountService.getCurrentUser());
        model.addAttribute("photoGalleryFull", false);

        Integer first_photo = 1;

        Integer numberOfImages = photoService.getPhotosFromProfile(profileService.findByProfileName(profileName)).size();

        if (numberOfImages == 10) {
            model.addAttribute("photoGalleryFull", true);
        }

        if (numberOfImages == 0) {
            first_photo = 0;
        }

        return "redirect:/accounts/" + profileName + "/photos/" + first_photo;
    }

    @GetMapping("/accounts/{profileName}/photos/{number}")
    public String viewPhoto(Model model, @PathVariable String profileName, @PathVariable Integer number) {

        Integer numberOfImages = photoService.getPhotosFromProfile(profileService.findByProfileName(profileName)).size();
        Integer previous = number - 1;
        Integer next = number + 1;
        Integer current = number;

        if (current == numberOfImages) {
            next = null;
        }
        if (current <= 1) {
            previous = null;
        }
        model.addAttribute("photoGalleryFull", false);

        if (numberOfImages == 10) {
            model.addAttribute("photoGalleryFull", true);
        }

        if (current >= 1) {
            model.addAttribute("caption", photoService.getPhotoFromProfile(profileService.findByProfileName(profileName), number).getCaption());
        }

        Profile profile = profileService.getProfileByProfileName(profileName);
        Account currentUser = accountService.getCurrentUser();
        Profile currentUserProfile = profileService.findByProfileName(currentUser.getProfileName());

        Photo photo = photoService.getPhotoFromProfile(profileService.findByProfileName(profileName), number);
        List<PhotoLike> likes = photoLikeService.getPhotoLikesByPhoto(photo);
        List<PhotoComment> comments = photoCommentService.getPhotoCommentsByPhoto(photo);

        Boolean liked = photoLikeService.alreadyLiked(photo, currentUserProfile);

        model.addAttribute("liked", liked);
        model.addAttribute("likeCount", likes.size());
        model.addAttribute("comments", comments);

        model.addAttribute("profile", profile);
        model.addAttribute("previous", previous);
        model.addAttribute("next", next);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("current", number);
        model.addAttribute("total_count", numberOfImages);

        return "photos";
    }

    @GetMapping(path = "/accounts/{profileName}/photos/{number}/content", produces = "image/*")
    @ResponseBody
    public byte[] getContent(@PathVariable String profileName, @PathVariable Integer number) {

        Profile profile = profileService.findByProfileName(profileName);

        return photoService.getPhotoFromProfile(profile, number).getContent();
    }

    @PostMapping("/accounts/{profileName}/photos")
    public String addPhoto(Model model, @PathVariable String profileName, @RequestParam String caption, @RequestParam("file") MultipartFile file) throws IOException {

        Integer numberOfImages = photoService.getPhotosFromProfile(profileService.findByProfileName(profileName)).size();
        model.addAttribute("profile", profileService.getProfileByProfileName(profileName));

        if (file.getBytes().length == 0) {
            model.addAttribute("error_message", "Et voi ladata tyhjää tiedostoa :/ ");
            return "redirect:/accounts/" + profileName + "/photos/" + numberOfImages;
            // ois kiva jos tän sais palauttamaan curent photon + ei näytä virheteksitä?
        }

        Photo newPhoto = new Photo();
        newPhoto.setContent(file.getBytes());
        newPhoto.setNumber(numberOfImages + 1);
        newPhoto.setProfile(profileService.findByProfileName(profileName));
        newPhoto.setCaption(caption);
        newPhoto.setProfilePhoto(false);
        photoService.save(newPhoto);

        numberOfImages = numberOfImages + 1;

        return "redirect:/accounts/" + profileName + "/photos/" + numberOfImages;
    }

    @PostMapping("/accounts/{profileName}/photos/{number}/setProfilePhoto")
    public String setProfilePhoto(Model model, @PathVariable String profileName, @PathVariable Integer number) {

        Photo photo = photoService.getPhotoFromProfile(profileService.getProfileByProfileName(profileName), number);
        Integer numberOfImages = photoService.getPhotosFromProfile(profileService.findByProfileName(profileName)).size();

        for (int i = 1; i <= numberOfImages; i++) {
            Photo p = (Photo) photoService.getPhotosFromProfile(profileService.findByProfileName(profileName)).get(i - 1);
            if (i == number) {
                p.setProfilePhoto(true);
            } else {
                p.setProfilePhoto(false);
            }
            photoService.save(p);
        }
        return "redirect:/accounts/" + profileName + "/photos/" + numberOfImages;
    }

    @PostMapping("/accounts/{profileName}/photos/{number}/deletePhoto")
    public String deletePhoto(Model model, @PathVariable String profileName, @PathVariable Integer number) {

        Photo photo = photoService.getPhotoFromProfile(profileService.getProfileByProfileName(profileName), number);
        photoService.delete(photo);

        Integer numberOfImages = photoService.getPhotosFromProfile(profileService.findByProfileName(profileName)).size();

        for (int i = 1; i <= numberOfImages; i++) {
            Photo p = (Photo) photoService.getPhotosFromProfile(profileService.findByProfileName(profileName)).get(i - 1);
            p.setNumber(i);
            photoService.save(p);
        }

        return "redirect:/accounts/" + profileName + "/photos/" + numberOfImages;
    }

}
