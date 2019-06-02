package gb.vhs.api.repository;


import gb.vhs.api.entity.MessageTemplate;
import gb.vhs.api.entity.User;
import gb.vhs.api.entity.UserProfile;
import gb.vhs.api.entity.enumeration.Enum;
import gb.vhs.api.repository.implementation.CustomRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends CustomRepository<UserProfile, Long> {

    List<UserProfile> getAllByUser(User user);
    List<UserProfile> getAllByIsGeneral(Boolean isGeneral);
}


