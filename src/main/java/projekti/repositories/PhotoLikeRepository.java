package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entities.Photo;
import projekti.entities.PhotoLike;
import projekti.entities.Profile;

public interface PhotoLikeRepository extends JpaRepository<PhotoLike, Long> {

    List<PhotoLike> findByProfileAndPhoto(Profile profile, Photo photo);
        
    boolean existsByPhotoAndProfile(Photo photo, Profile profile);
    
    List<PhotoLike> findByPhoto(Photo photo);
}
