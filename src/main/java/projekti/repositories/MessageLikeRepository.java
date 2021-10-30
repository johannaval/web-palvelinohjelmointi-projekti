package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entities.Message;
import projekti.entities.MessageLike;
import projekti.entities.Profile;

public interface MessageLikeRepository extends JpaRepository<MessageLike, Long> {

    List<MessageLike> findByProfileAndMessage(Profile profile, Message message);

    boolean existsByMessageAndProfile(Message message, Profile profile);

    List<MessageLike> findByMessage(Message message);
}
