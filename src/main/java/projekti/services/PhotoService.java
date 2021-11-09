package projekti.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.entities.Photo;
import projekti.entities.Profile;
import projekti.repositories.PhotoRepository;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    public void save(Photo photo) {
        photoRepository.save(photo);
        photoRepository.flush();
    }

    public Photo getOne(Long id) {
        return photoRepository.getOne(id);
    }

    public List getPhotosFromProfile(Profile profile) {
        return photoRepository.findByProfile(profile);
    }

    public Photo getPhotoFromProfile(Profile profile, Integer number) {
        return photoRepository.findByProfileAndNumber(profile, number);
    }

    public void delete(Photo photo) {
        photoRepository.delete(photo);
        photoRepository.flush();
    }

    public Photo getProfilePhoto(Profile profile) {
        List<Photo> photos = photoRepository.findByProfile(profile);

        for (Photo photo : photos) {
            if (photo.getProfilePhoto()) {
                return photo;
            }
        }
        return null;
    }

}
