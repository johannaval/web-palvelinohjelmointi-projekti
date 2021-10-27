package projekti.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.entities.Profile;
import projekti.entities.Account;
import projekti.repositories.AccountRepository;
import projekti.repositories.ProfileRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Profile getProfileByProfileName(String profileName) {
        return profileRepository.findByProfileName(profileName);
    }

    public Profile findByProfileName(String profileName) {
        return profileRepository.findByProfileName(profileName);
    }

    public ArrayList<Profile> findProfilesByName(String name) {
        ArrayList<Account> accounts = accountRepository.findByName(name);
        ArrayList<Profile> profiles = new ArrayList<>();

        for (Account a : accounts) {
            profiles.add(profileRepository.findByProfileName(a.getProfileName()));
        }
        return profiles;
    }

    public void updateProfile(Profile profile) {

        profileRepository.save(profile);
        //   profileRepository.flush();
    }

    public void saveProfile(Account account) {

        Profile newProfile = new Profile();

        List<Profile> followers = new ArrayList<>();
        List<Profile> following = new ArrayList<>();

        newProfile.setAccount(account);
        newProfile.setProfileName(account.getProfileName());
        newProfile.setFollowers(followers);
        newProfile.setFollowing(following);

        profileRepository.save(newProfile);
        //     profileRepository.flush();
    }
}