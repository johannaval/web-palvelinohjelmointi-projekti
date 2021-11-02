package projekti.entities;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageComment extends AbstractPersistable<Long> {

    @ManyToOne
    private Message message;

    @ManyToOne
    private Profile profile;

    private LocalDateTime date = LocalDateTime.now();

    private String dateString;

    //@NotEmpty
    //@Column(columnDefinition="TEXT")
    //@Size(min = 1, max = 250, message = "Kommentissa pitää olla 1-150 merkkiä")
    private String commentContent;
}
