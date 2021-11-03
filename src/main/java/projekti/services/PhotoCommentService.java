package projekti.services;

import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.entities.Message;
import projekti.entities.Photo;
import projekti.entities.PhotoComment;
import projekti.entities.PhotoLike;
import projekti.entities.Profile;
import projekti.repositories.PhotoCommentRepository;
import projekti.repositories.PhotoLikeRepository;
import projekti.repositories.ProfileRepository;

@Service
public class PhotoCommentService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PhotoCommentRepository photoCommentRepository;

    public List getPhotoCommentsByProfiles(Photo photo, Profile profile) {

        List<PhotoComment> comments = photoCommentRepository.findByProfileAndPhoto(profile, photo);

        return comments;
    }

    public List getPhotoCommentsByPhoto(Photo photo) {

        List<PhotoComment> comments = photoCommentRepository.findByPhoto(photo);
        comments.sort(Comparator.comparing(PhotoComment::getDate).reversed());

        if (comments.size() > 10) {
            return comments.subList(0, 10);
        }

        return comments;
    }

    public void save(PhotoComment photoComment) {

        photoCommentRepository.save(photoComment);
    }

    public void deleteComments(Photo photo, Profile profile) {

        List<PhotoComment> comments = photoCommentRepository.findByProfileAndPhoto(profile, photo);
        
        for (PhotoComment comment : comments) {
            photoCommentRepository.delete(comment);
        }        
    }
}
