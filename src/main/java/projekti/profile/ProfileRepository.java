package projekti.profile;

import projekti.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Account, Long> {

    Profile findByProfileName(String profileName);
}
