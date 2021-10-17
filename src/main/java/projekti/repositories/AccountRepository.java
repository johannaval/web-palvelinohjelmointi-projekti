package projekti.repositories;

import java.util.ArrayList;
import projekti.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);

    Account findByProfileName(String profileName);

    ArrayList<Account> findByName(String name);
    
}
