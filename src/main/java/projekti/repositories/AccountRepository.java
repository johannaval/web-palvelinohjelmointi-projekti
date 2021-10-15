package projekti.repositories;

import projekti.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);
    Account findByProfileName(String profileName);
}
