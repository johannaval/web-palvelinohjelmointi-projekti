package projekti.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.entities.Follow;
import projekti.entities.Message;
import projekti.entities.MessageLike;
import projekti.entities.Profile;
import projekti.repositories.FollowRepository;
import projekti.repositories.MessageRepository;
import projekti.repositories.ProfileRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List getMessagesByProfiles(List<Profile> profiles) {

        List<Message> messages = messageRepository.findByProfileIn(profiles);
        messages.sort(Comparator.comparing(Message::getDate).reversed());

        if (messages.size() > 25) {
            return messages.subList(0, 25);
        }
        return messages;
    }

    public void save(Message message) {

        messageRepository.save(message);
    }

    public Message findMessageById(Long id) {

        List<Message> message = messageRepository.findAllById(id);

        return message.get(0);
    }

    public Integer countMessageLikes(Long id) {

        List<Message> message = messageRepository.findAllById(id);

        Message m = message.get(0);

        return m.getLikes().size();
    }

    public void adMessageLike(Message message) {

        /*  System.out.println("!!!!!! :/");

        List<MessageLike>likes=message.getLikes();
        likes.add(messageLike);
        message.setLikes(likes);

        messageRepository.save(message);

        System.out.println("size: " + message.getLikes().size());
        
        for (int i = 0; i<message.getLikes().size();i++){
            System.out.println(i+":"+message.getLikes().get(i));
        }

        System.out.println("sisältö: " + message.getLikes()); */
        Integer likes = message.getLikeCount();
        message.setLikeCount(likes + 1);

        messageRepository.save(message);

    }
    
     public void addMessageLike(Message message, MessageLike messageLike) {

        List<MessageLike>likes=message.getLikes();
        likes.add(messageLike);
        message.setLikes(likes);
        
        for (int i = 0; i<message.getLikes().size();i++){
            System.out.println(i+":"+message.getLikes().get(i));
        }

        messageRepository.save(message);
    }
}
