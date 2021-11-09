package projekti.services;

import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.entities.Message;
import projekti.entities.MessageComment;
import projekti.repositories.MessageCommentRepository;
import projekti.repositories.ProfileRepository;

@Service
public class MessageCommentService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private MessageCommentRepository messageCommentRepository;

    public List getMessageCommentsByMessage(Message message) {

        List<MessageComment> comments = messageCommentRepository.findByMessage(message);
        comments.sort(Comparator.comparing(MessageComment::getDate).reversed());

        if (comments.size() > 10) {
            return comments.subList(0, 10);
        }
        return comments;
    }

    public void save(MessageComment messageComment) {

        messageCommentRepository.save(messageComment);
    }
}
