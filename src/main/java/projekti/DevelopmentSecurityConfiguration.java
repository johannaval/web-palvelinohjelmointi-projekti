package projekti;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
public class DevelopmentSecurityConfiguration extends WebSecurityConfigurerAdapter {
// tämä luokka oli alumperin jo täällä, mä tein SecurityConfiguration.java luokan, joko poista tää tai korvaa tolla
}

 /*   @Override
    public void configure(WebSecurity sec) throws Exception {
        // Pyyntöjä ei tarkasteta
        sec.ignoring().antMatchers("/**");
    }
}
 */