package projekti.controllers;

import projekti.services.AccountService;
import projekti.entities.Account;
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
import projekti.entities.Account;
import projekti.services.ProfileService;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProfileService profileService;

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

    @GetMapping("/login_error")
    public String loginErrorView(Model model) {

        model.addAttribute("error_message", "Käyttäjänimi tai salasana on väärin");

        return "login_form";
    }

    @GetMapping("/accounts")
    public String list(Model model) {

        model.addAttribute("accounts", accountService.getAllAccounts());
        return "accounts";
    }

    @PostMapping("/registrations")
    public String register(@Valid @ModelAttribute Account account, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration_form";
        }
        if (accountService.getAccountByUsername(account.getUsername()) != null) {

            model.addAttribute("error_message", "Voi rähmä, käyttäjänimi on jo varattu!");
            return "registration_form";
        }
        if (accountService.getAccountByProfileName(account.getProfileName()) != null) {

            model.addAttribute("error_message", "Voi rähmä, profiilin tunnus on jo varattu!");
            return "registration_form";
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        
        accountService.saveAccount(account);
        profileService.saveProfile(account);

        return "redirect:/login";
    }
}
