package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entities.Photo;
import projekti.entities.PhotoComment;
import projekti.entities.Profile;


public interface PhotoCommentRepository extends JpaRepository<PhotoComment, Long> {

    List<PhotoComment> findByProfileAndPhoto(Profile profile, Photo photo);
            
    List<PhotoComment> findByPhoto(Photo photo);
}
