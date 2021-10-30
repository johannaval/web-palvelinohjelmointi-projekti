package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entities.Message;
import projekti.entities.MessageComment;
import projekti.entities.Photo;
import projekti.entities.PhotoComment;
import projekti.entities.Profile;


public interface MessageCommentRepository  extends JpaRepository<MessageComment, Long> {
            
    List<MessageComment> findByMessage(Message message);

}
