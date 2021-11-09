package projekti.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.hibernate.annotations.Type;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Photo extends AbstractPersistable<Long> {

    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] content;

    @ManyToOne
    private Profile profile;

    private Boolean profilePhoto;

    private Integer number;

    private String caption;
}
