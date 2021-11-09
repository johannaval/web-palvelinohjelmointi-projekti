package projekti.controllers;

import projekti.services.AccountService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    
    @GetMapping("/login")
    public String loginView() {

        return "login_form";
    }

    @GetMapping("/login/")
    public String loginErrorView(Model model) {

        model.addAttribute("error_message", "Käyttäjänimi tai salasana on väärin");

        return "login_form";
    }

    @GetMapping("/registration")
    public String registrationView(@ModelAttribute Account account) {

        return "registration_form";
    }

    @PostMapping("/registration")
    public String register(@Valid @ModelAttribute Account account, BindingResult bindingResult, Model model) {

        Account usernameAlreadyInUse = accountService.getAccountByUsername(account.getUsername());
        Account profileNameAlreadyInUse = accountService.getAccountByProfileName(account.getProfileName());

        if (usernameAlreadyInUse != null || profileNameAlreadyInUse != null || bindingResult.hasErrors()) {

            if (usernameAlreadyInUse != null) {
                model.addAttribute("error_message_username", "Tämä käyttäjänimi on jo varattu");
            }
            if (profileNameAlreadyInUse != null) {
                model.addAttribute("error_message_profileName", "Tämä profiilin tunnus on jo varattu");
            }
            return "registration_form";
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountService.saveAccount(account);
        profileService.saveProfile(account);

        return "redirect:/login";
    }
}
