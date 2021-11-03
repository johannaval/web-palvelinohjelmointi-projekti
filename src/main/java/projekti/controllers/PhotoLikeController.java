package projekti.controllers;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.entities.Account;
import projekti.entities.Follow;
import projekti.entities.Photo;
import projekti.entities.PhotoLike;
import projekti.entities.Profile;
import projekti.services.AccountService;
import projekti.services.PhotoLikeService;
import projekti.services.PhotoService;
import projekti.services.ProfileService;

@Controller
public class PhotoLikeController {

    @Autowired
    PhotoService photoService;

    @Autowired
    ProfileService profileService;

    @Autowired
    PhotoLikeService photoLikeService;

    @Autowired
    AccountService accountService;

    @PostMapping("/accounts/{profileName}/photos/{number}/like")
    public String addLike(@PathVariable String profileName, @PathVariable Integer number) {

        Profile profile = profileService.findByProfileName(profileName);
        Photo photo = photoService.getPhotoFromProfile(profileService.findByProfileName(profileName), number);

        Account currentUser = accountService.getCurrentUser();
        Profile currentUserProfile = profileService.findByProfileName(currentUser.getProfileName());

        PhotoLike photoLike = new PhotoLike();
        photoLike.setPhoto(photo);
        photoLike.setProfile(currentUserProfile);

        photoLikeService.save(photoLike);
        

        return "redirect:/accounts/" + profileName + "/photos/" + number;

    }
    
      @PostMapping("/accounts/{profileName}/photos/{number}/delete_like")
    public String delete_like(@PathVariable String profileName, @PathVariable Integer number) {

        Profile profile = profileService.findByProfileName(profileName);
        Photo photo = photoService.getPhotoFromProfile(profileService.findByProfileName(profileName), number);

        List<PhotoLike> likes = photoLikeService.getPhotoLikesByPhoto(photo);

        Account currentUser = accountService.getCurrentUser();
        Profile currentUserProfile = profileService.findByProfileName(currentUser.getProfileName());

        Photo liked = photoService.getPhotoFromProfile(profile, number);
        List<PhotoLike> list_likes = photoLikeService.getPhotoLikesByPhoto(liked);
        
        for (PhotoLike like : list_likes){
            if(like.getPhoto().getProfile().getProfileName().equals(currentUserProfile.getProfileName())){
                photoLikeService.deleteLike(like);
            }            
        }
        
      
        return "redirect:/accounts/" + profileName + "/photos/" + number;

    }
}
