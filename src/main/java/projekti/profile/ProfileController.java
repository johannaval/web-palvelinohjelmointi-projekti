package projekti.profile;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.account.AccountRepository;

@Controller
public class ProfileController {

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/accounts/{profileName}")
    public String viewExam(Model model, @PathVariable String profileName) {

        model.addAttribute("account", accountRepository.findByProfileName(profileName));
        return "profile";
    }
}
