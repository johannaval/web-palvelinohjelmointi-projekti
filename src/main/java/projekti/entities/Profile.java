package projekti.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
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

    // @ElementCollection(targetClass = Profile.class)
    //  @ManyToMany()
    @ManyToMany()
    private List<Profile> followers = new ArrayList<>();

    // @ElementCollection(targetClass = Profile.class)
    @ManyToMany()
    private List<Profile> following = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Photo> photos = new ArrayList<>();
}
