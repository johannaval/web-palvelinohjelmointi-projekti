package projekti.entities;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageComment  extends AbstractPersistable<Long> {

    @ManyToOne
    private Message message;

    @ManyToOne
    private Profile profile;

    private LocalDateTime date = LocalDateTime.now();

    //@NotEmpty
    //@Column(columnDefinition="TEXT")
    private String content;
}
