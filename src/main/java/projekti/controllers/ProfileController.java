package projekti.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.entities.Account;
import projekti.entities.Follow;
import projekti.entities.Photo;
import projekti.entities.Profile;
import projekti.repositories.AccountRepository;
import projekti.repositories.ProfileRepository;
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

        model.addAttribute("profile", profile);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("alreadyFollowing", false);
        model.addAttribute("profilePhoto", null);

        Photo profilePhoto = photoService.getProfilePhoto(profile);

        List<Follow> followers = followService.findByFollowing(profileService.findByProfileName(profileName));
        model.addAttribute("number_of_followers", followers.size());

        if (profilePhoto != null) {
            model.addAttribute("profilePhoto", profilePhoto.getNumber());
        }

        if (currentUser.getProfileName().equals(profileName)) {
            model.addAttribute("followers", followers);
        }

        if (followService.findByFollowerAndFollowing(currentUserProfile, profile)) {
            model.addAttribute("alreadyFollowing", true);
        } else {
            model.addAttribute("alreadyFollowing", false);
        }
        return "profile";
    }

    @GetMapping("/findProfile")
    public String indexView(Model model) {

        model.addAttribute("currentUser", accountService.getCurrentUser());
        return "findProfiles";
    }

    @GetMapping("/findProfile/seach")
    public String indexView(Model model, @RequestParam String name) {

        model.addAttribute("profiles", profileService.findProfilesByName(name));
        model.addAttribute("currentUser", accountService.getCurrentUser());

        return "findProfiles";
    }

    /*
    @Transactional
    @PostMapping("/accounts/{profileName}")
    public String followProfile(@PathVariable String profileName) {

        Profile followProfile = profileService.findByProfileName(profileName);
        Profile currentUserProfile = profileService.findByProfileName(accountService.getCurrentUser().getProfileName());

        if (!currentUserProfile.getFollowing().contains(followProfile)) {

            followProfile.getFollowers().add(currentUserProfile);
            currentUserProfile.getFollowing().add(followProfile);

            profileService.updateProfile(followProfile);
            profileService.updateProfile(currentUserProfile);
        }

        return "redirect:/accounts/{profileName}";
    }

    @Transactional
    @PostMapping("/accounts/{profileName}/delete_follower/{profileName_to_delete}")
    public String deleteFollower(@PathVariable String profileName, @PathVariable String profileName_to_delete) {

        Profile delete_follower = profileService.findByProfileName(profileName_to_delete);
        Profile currentUserProfile = profileService.findByProfileName(accountService.getCurrentUser().getProfileName());

        if (currentUserProfile.getFollowers().contains(delete_follower)) {

            currentUserProfile.getFollowers().remove(delete_follower);
            delete_follower.getFollowing().remove(currentUserProfile);

            profileService.updateProfile(delete_follower);
            profileService.updateProfile(currentUserProfile);
        }

        return "redirect:/accounts/{profileName}";
    }*/
}
