package projekti.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.entities.Follow;
import projekti.entities.Profile;
import projekti.services.AccountService;
import projekti.services.FollowService;
import projekti.services.ProfileService;

@Controller
public class FollowController {

    @Autowired
    AccountService accountService;

    @Autowired
    ProfileService profileService;

    @Autowired
    FollowService followService;

    @PostMapping("/accounts/{profileName}/")
    public String followProfile(@PathVariable String profileName) {

        Profile following = profileService.findByProfileName(profileName);
        Profile follower = profileService.findByProfileName(accountService.getCurrentUser().getProfileName());

        if (!followService.findByFollowerAndFollowing(follower, following)) {
            Follow follow = new Follow();
            follow.setFollower(profileService.findByProfileName(accountService.getCurrentUser().getProfileName()));
            follow.setFollowing(profileService.findByProfileName(profileName));
            LocalDateTime following_time = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = following_time.format(formatter);
            follow.setTime(formatDateTime);
            followService.save(follow);
        }
        return "redirect:/accounts/{profileName}";

    }

    @PostMapping("/accounts/{profileName}/delete_follower/{profileName_to_delete}")
    public String deleteFollow(@PathVariable String profileName, @PathVariable String profileName_to_delete) {

        Profile following = profileService.findByProfileName(profileName);
        Profile follower = profileService.findByProfileName(profileName_to_delete);

        Follow follow_to_delete = followService.getFollow(following, follower);
        followService.delete(follow_to_delete);

        return "redirect:/accounts/{profileName}";

    }

    @PostMapping("/accounts/{profileName}/delete_following/{profileName_to_delete}")
    public String deleteFollowing(@PathVariable String profileName, @PathVariable String profileName_to_delete) {

        Profile following = profileService.findByProfileName(profileName_to_delete);
        Profile follower = profileService.findByProfileName(profileName);

        Follow follow_to_delete = followService.getFollowing(following, follower);
        followService.delete(follow_to_delete);

        return "redirect:/accounts/{profileName}";

    }
}
