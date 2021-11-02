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
public class PhotoComment extends AbstractPersistable<Long> {

    @ManyToOne
    private Photo photo;

    @ManyToOne
    private Profile profile;

    private LocalDateTime date = LocalDateTime.now();

    private String dateString;

    //@NotEmpty
    //@Column(columnDefinition="TEXT")
    private String content;
}
