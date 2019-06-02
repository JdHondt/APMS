package gb.vhs.api.service;

import gb.vhs.api.entity.*;
import gb.vhs.api.entity.enumeration.Enum;
import gb.vhs.api.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ActivityTriggerAnalyzer {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UpdateProfile updateProfile;

    public void activityTriggerAnalyzer(PerformedActivity performedActivity){
        if (performedActivity.getActivityTrigger() != Enum.ActivityTrigger.MESSAGE){
            return;
        }
        User user = performedActivity.getUser();
        Activity activity = performedActivity.getActivity();

        List<Message> recentMessages = user.getMessages();

        if (!CollectionUtils.isEmpty(recentMessages)){
            recentMessages.sort(Comparator.comparing(Message::getSentAt).reversed());
            for(Message m : recentMessages){
                long age_days = ChronoUnit.DAYS.between(LocalDateTime.now(), m.getSentAt());
                if(age_days <= 3 && m.getActivity().getActivityType() == activity.getActivityType()){
                    Enum.PersuasionProfile personality_to_update = m.getMessageTemplate().getPersuasionProfile();
                    updateProfile.mainUpdateProfile(user, 1, personality_to_update);
                }
            }
        }

    }
}
