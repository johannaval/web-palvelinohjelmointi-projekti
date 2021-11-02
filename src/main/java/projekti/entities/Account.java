package projekti.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractPersistable<Long> {

    @Size(min = 3, max = 50, message = "Käyttäjänimessä pitää olla 3-50 merkkiä")
    private String username;

    @NotEmpty(message = "Salasana ei voi olla tyhjä")
    private String password;

    @Size(min = 3, max = 50, message = "Nimessä pitää olla 3-50 merkkiä")
    private String name;

    @Size(min = 3, max = 50, message = "Profiilin nimessä pitää olla 3-50 merkkiä")
    private String profileName;

    @OneToOne
    private Profile profile;
}
