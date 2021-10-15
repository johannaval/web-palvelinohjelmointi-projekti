package projekti.account;

import projekti.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);
    Account findByProfileName(String profileName);
}
