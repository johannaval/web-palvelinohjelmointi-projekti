package projekti.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends AbstractPersistable<Long> {

    @OneToOne
    private Account account;

    private String profileName;

    @ManyToMany()
    private List<Profile> followers = new ArrayList<>();

    @ManyToMany()
    private List<Profile> following = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Photo> photos = new ArrayList<>();
}
