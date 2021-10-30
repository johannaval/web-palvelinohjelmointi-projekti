package projekti.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Message extends AbstractPersistable<Long> {

    @ManyToOne
    private Profile profile;

    //@NotEmpty
    //@Column(columnDefinition="TEXT")
    private String content;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime date = LocalDateTime.now();

    private Integer likeCount;

    @OneToMany(mappedBy = "message")
    private List<MessageLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "message")
    private List<MessageComment> comments = new ArrayList<>();

}
