package projekti.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.entities.Message;
import projekti.entities.MessageLike;
import projekti.entities.Profile;
import projekti.repositories.MessageLikeRepository;
import projekti.repositories.ProfileRepository;

@Service
public class MessageLikeService {
 
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private MessageLikeRepository messageLikeRepository;

    public List getMessageLikesByProfiles(Message message, Profile profile) {

        //käyttääkö tätä edes mikää
        List<MessageLike> likes = messageLikeRepository.findByProfileAndMessage(profile, message);

        return likes;
    }

    public List getMessageLikesByMessage(Message message) {

        List<MessageLike> likes = messageLikeRepository.findByMessage(message);

        return likes;
    }

    public boolean alreadyLiked(Message message, Profile profile) {

        return messageLikeRepository.existsByMessageAndProfile(message, profile);

    }

    public void save(MessageLike messageLike) {

        messageLikeRepository.save(messageLike);
    }
}
