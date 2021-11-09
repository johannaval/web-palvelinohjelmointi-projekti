package projekti.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.entities.Photo;
import projekti.entities.PhotoLike;
import projekti.entities.Profile;
import projekti.repositories.PhotoLikeRepository;
import projekti.repositories.ProfileRepository;

@Service
public class PhotoLikeService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PhotoLikeRepository photoLikeRepository;

    public List getPhotoLikesByProfiles(Photo photo, Profile profile) {

        List<PhotoLike> likes = photoLikeRepository.findByProfileAndPhoto(profile, photo);

        return likes;
    }

    public List getPhotoLikesByPhoto(Photo photo) {

        List<PhotoLike> likes = photoLikeRepository.findByPhoto(photo);

        return likes;
    }

    public boolean alreadyLiked(Photo photo, Profile profile) {

        return photoLikeRepository.existsByPhotoAndProfile(photo, profile);

    }

    public void save(PhotoLike photoLike) {

        photoLikeRepository.save(photoLike);
    }

    public void deleteLikes(Photo photo, Profile profile) {

        List<PhotoLike> likes = photoLikeRepository.findByProfileAndPhoto(profile, photo);

        for (PhotoLike like : likes) {
            photoLikeRepository.delete(like);
        }
    }

    public void deleteLike(PhotoLike like) {
        
        photoLikeRepository.delete(like);
    }
}
