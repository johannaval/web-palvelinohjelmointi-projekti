package projekti.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Follow extends AbstractPersistable<Long> {

    private String time;
    
    @ManyToOne
    private Profile follower;

    @ManyToOne
    private Profile following;

}
