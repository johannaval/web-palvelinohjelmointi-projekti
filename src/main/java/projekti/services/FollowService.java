package projekti.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.entities.Follow;
import projekti.entities.Profile;
import projekti.repositories.FollowRepository;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    public Boolean findByFollowerAndFollowing(Profile follower, Profile following) {

        if (followRepository.findByFollowerAndFollowing(follower, following).size()>0) {
            return true;
        }
        return false;
    }

    public void save(Follow follow) {

        followRepository.save(follow);
    }

    public Follow getFollow(Profile following, Profile follower) {

        List<Follow> follows = followRepository.findByFollowing(following);

        for (Follow follow : follows) {
            if (follow.getFollower().equals(follower)) {
                return follow;
            }
        }
        return null;
    }

    public void delete(Follow follow) {

        followRepository.delete(follow);
    }

    public List<Follow> findByFollower(Profile follower) {

        return followRepository.findByFollower(follower);
    }

    public List<Follow> findByFollowing(Profile following) {

        return followRepository.findByFollowing(following);
    }
}
