package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projekti.entities.Follow;
import projekti.entities.Profile;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    public List<Follow> findByFollowerAndFollowing(Profile follower, Profile following);

    public List<Follow> findByFollower(Profile follower);

    public List<Follow> findByFollowing(Profile following);
}
