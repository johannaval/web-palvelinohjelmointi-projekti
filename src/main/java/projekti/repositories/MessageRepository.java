package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entities.Message;
import projekti.entities.Profile;

public interface MessageRepository extends JpaRepository<Message, Long> {

    public List<Message> findByProfileIn(List<Profile> profiles);

    public List<Message> findAllById(Long id);

}
