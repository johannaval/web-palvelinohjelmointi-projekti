package projekti.entities;

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
public class MessageLike  extends AbstractPersistable<Long>{
    
    @ManyToOne
    private Message message;
    
    @ManyToOne
    private Profile profile;
    
}
