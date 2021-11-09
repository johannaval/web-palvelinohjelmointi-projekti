package projekti.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.Lob;
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

    //lokaalia testausta varten ->
    //@Lob
    //@Basic(fetch = FetchType.LAZY) <-
    @Type(type = "org.hibernate.type.BinaryType") // Herokua varten
    private byte[] content;

    @ManyToOne
    private Profile profile;

    private Boolean profilePhoto;

    private Integer number;

    private String caption;
}
