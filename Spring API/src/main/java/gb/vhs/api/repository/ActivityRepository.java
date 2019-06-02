package gb.vhs.api.repository;


import gb.vhs.api.entity.Activity;
import gb.vhs.api.entity.MessageFeedback;
import gb.vhs.api.entity.enumeration.Enum;
import gb.vhs.api.repository.implementation.CustomRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends CustomRepository<Activity, Long> {

    public Activity findFirstById(Long id);
    public Activity findFirstByActivityType(Enum.ActivityType activityType);

}


