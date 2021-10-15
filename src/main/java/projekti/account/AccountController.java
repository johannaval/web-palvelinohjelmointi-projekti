package projekti.account;

import projekti.account.Account;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/registrations")
    public String registrationView(@ModelAttribute Account account) {

        return "registration_form";
    }

    @GetMapping("/login")
    public String loginView() {

        return "login_form";
    }

    @GetMapping("/index")
    public String indexView() {

        return "index";
    }

    @GetMapping("/accounts")
    public String list(Model model) {

        model.addAttribute("accounts", accountService.getAllAccounts());
        return "accounts";
    }

    @PostMapping("/registrations")
    public String register(@Valid @ModelAttribute Account account, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "registration_form";
        }

        if (accountService.getAccountByUsername(account.getUsername()) != null) {
            return "registration_form";
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountService.saveAccount(account);

        return "redirect:/login";
    }
    /*
    @PostMapping("/login")
    public String login(@Valid @RequestParam String username, @RequestParam String password) {

        Account account = accountService.getAccountByUsername(username);

        if (account != null) {
            if (account.getPassword().equals(password)) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                return "index";
            }
        }
        return "login_form";
    }*/
}
