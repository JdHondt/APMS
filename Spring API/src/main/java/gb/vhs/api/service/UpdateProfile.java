package gb.vhs.api.service;

import gb.vhs.api.entity.*;
import gb.vhs.api.entity.enumeration.Enum;
import gb.vhs.api.repository.UserProfileRepository;
import gb.vhs.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class UpdateProfile {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UserRepository userRepository;

    public void mainUpdateProfile(User u, Integer updateValue, Enum.PersuasionProfile personalityToUpdate){

        List<UserProfile> generalProfiles = userProfileRepository.getAllByIsGeneral(true);
        generalProfiles.sort(Comparator.comparing(UserProfile::getTimestamp).reversed());
        UserProfile latestGeneralProfile = generalProfiles.get(0);

        List<UserProfile> userProfiles = userProfileRepository.getAllByUser(u);

        userProfiles.sort(Comparator.comparing(UserProfile::getTimestamp).reversed());
        UserProfile latestProfile = userProfiles.get(0);

        updateProfile(latestGeneralProfile, updateValue, personalityToUpdate);

        if (LocalDate.now().isAfter(LocalDate.of(2019, 4, 6)) &&
        u.getTreatmentGroup() == Enum.TreatmentGroup.TREATMENT) {
            updateProfile(latestProfile, updateValue, personalityToUpdate);
            u.setUpdates(u.getUpdates() + 1);
            userRepository.save(u);
        }
    }

    private void updateProfile(UserProfile userProfile, Integer updateValue, Enum.PersuasionProfile persuasionProfile){
        UserProfile newUserProfile = new UserProfile(userProfile.getExtraversion_A(), userProfile.getExtraversion_B(),
                userProfile.getAgreeableness_A(), userProfile.getAgreeableness_B(),
                userProfile.getConscientiousness_A(), userProfile.getConscientiousness_B(),
                userProfile.getNeuroticism_A(), userProfile.getNeuroticism_B(),
                userProfile.getOpenness_A(), userProfile.getOpenness_B());
        newUserProfile.setUser(userProfile.getUser());
        newUserProfile.setIsGeneral(userProfile.getIsGeneral());

        if (persuasionProfile == Enum.PersuasionProfile.EXTRAVERSION) {
            newUserProfile.setExtraversion_A(newUserProfile.getExtraversion_A() + updateValue);
            newUserProfile.setExtraversion_B(newUserProfile.getExtraversion_B() + (1 - updateValue));
            userProfileRepository.save(newUserProfile);
        }else if (persuasionProfile == Enum.PersuasionProfile.AGREEABLENESS) {
            newUserProfile.setAgreeableness_A(newUserProfile.getAgreeableness_A() + updateValue);
            newUserProfile.setAgreeableness_B(newUserProfile.getAgreeableness_B() + (1 - updateValue));
            userProfileRepository.save(newUserProfile);
        }else if (persuasionProfile == Enum.PersuasionProfile.CONSCIENTIOUSNESS) {
            newUserProfile.setConscientiousness_A(newUserProfile.getConscientiousness_A() + updateValue);
            newUserProfile.setConscientiousness_B(newUserProfile.getConscientiousness_B() + (1 - updateValue));
            userProfileRepository.save(newUserProfile);
        }else if (persuasionProfile == Enum.PersuasionProfile.NEUROTICISM) {
            newUserProfile.setNeuroticism_A(newUserProfile.getNeuroticism_A() + updateValue);
            newUserProfile.setNeuroticism_B(newUserProfile.getNeuroticism_B() + (1 - updateValue));
            userProfileRepository.save(newUserProfile);
        }else if (persuasionProfile == Enum.PersuasionProfile.OPENNESS) {
            newUserProfile.setOpenness_A(newUserProfile.getOpenness_A() + updateValue);
            newUserProfile.setOpenness_B(newUserProfile.getOpenness_B() + (1 - updateValue));
            userProfileRepository.save(newUserProfile);
        }
    }
}
