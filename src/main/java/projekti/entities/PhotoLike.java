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
public class PhotoLike  extends AbstractPersistable<Long>{
    
    @ManyToOne
    private Photo photo;
    
    @ManyToOne
    private Profile profile;
    
}
