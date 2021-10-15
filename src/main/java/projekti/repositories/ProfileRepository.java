package projekti.repositories;

import projekti.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entities.Profile;

public interface ProfileRepository extends JpaRepository<Account, Long> {

    Profile findByProfileName(String profileName);
}
