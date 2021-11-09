package projekti.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.entities.Account;
import projekti.entities.Follow;
import projekti.entities.Photo;
import projekti.entities.Profile;
import projekti.services.AccountService;
import projekti.services.FollowService;
import projekti.services.PhotoService;
import projekti.services.ProfileService;

@Controller
public class ProfileController {

    @Autowired
    AccountService accountService;

    @Autowired
    ProfileService profileService;

    @Autowired
    PhotoService photoService;

    @Autowired
    FollowService followService;

    @GetMapping("/accounts/{profileName}")
    public String viewProfile(Model model, @PathVariable String profileName) {

        Account currentUser = accountService.getCurrentUser();
        Profile profile = profileService.findByProfileName(profileName);
        Profile currentUserProfile = profileService.findByProfileName(currentUser.getProfileName());

        Photo profilePhoto = photoService.getProfilePhoto(profile);

        List<Follow> followers = followService.findByFollowing(profileService.findByProfileName(profileName));
        List<Follow> following = followService.findByFollower(profileService.findByProfileName(profileName));

        model.addAttribute("profilePhoto", null);

        if (profilePhoto != null) {
            model.addAttribute("profilePhoto", profilePhoto.getNumber());
        }
        model.addAttribute("number_of_followers", followers.size());
        model.addAttribute("number_of_following", following.size());
        model.addAttribute("profile", profile);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("alreadyFollowing", false);
        model.addAttribute("followers", followers);
        model.addAttribute("following", following);
        model.addAttribute("profileBelongsToLoggedInUser", false);
        model.addAttribute("alreadyFollowing", false);

        if (currentUserProfile.equals(profile)) {
            model.addAttribute("profileBelongsToLoggedInUser", true);
        }

        if (followService.findByFollowerAndFollowing(currentUserProfile, profile)) {
            model.addAttribute("alreadyFollowing", true);
        }
        
        return "profile";
    }

    @GetMapping("/findProfile")
    public String findProfilesView(Model model) {

        model.addAttribute("currentUser", accountService.getCurrentUser());
        return "findProfiles";
    }

    @GetMapping("/findProfile/seach")
    public String findProfilesSeach(Model model, @RequestParam String name) {

        List<Profile> profiles = profileService.findByProfileNameContains(name);

        model.addAttribute("profiles", profiles);
        model.addAttribute("currentUser", accountService.getCurrentUser());

        return "findProfiles";
    }
}
