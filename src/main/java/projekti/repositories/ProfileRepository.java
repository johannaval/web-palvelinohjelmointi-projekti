package projekti.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findByProfileName(String profileName);
}
