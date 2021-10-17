package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entities.Photo;
import projekti.entities.Profile;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
   
    public List<Photo> findByProfile(Profile profile);
   
    public Photo findByProfileAndNumber(Profile profile, Integer number);
}