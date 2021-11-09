package projekti.services;

import projekti.repositories.AccountRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import projekti.entities.Account;
import projekti.repositories.ProfileRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProfileRepository profileRepository;

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

    public Account getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        return accountRepository.findByUsername(name);
    }
}
