package projekti.account;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account getAccountByUsername(String username) {

        Account account = accountRepository.findByUsername(username);
        return account;
    }

    public Account getAccountByProfileName(String profileName) {

        Account account = accountRepository.findByProfileName(profileName);
        return account;
    }

    public List getAllAccounts() {

        return accountRepository.findAll();
    }

    public void saveAccount(Account account) {

        accountRepository.save(account);
    }
}
