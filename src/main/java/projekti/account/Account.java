package projekti.account;

import javax.persistence.Entity;
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

    @Size(min = 3, max = 50)
    private String username;

    @NotEmpty()
    private String password;

    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 3, max = 50)
    private String profileName;
}
