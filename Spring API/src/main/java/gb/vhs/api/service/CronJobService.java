package gb.vhs.api.service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import gb.vhs.api.entity.TimeInterval;
import gb.vhs.api.entity.User;
import gb.vhs.api.entity.UserProfile;
import gb.vhs.api.entity.enumeration.Enum;
import gb.vhs.api.repository.TimeIntervalRepository;
import gb.vhs.api.repository.UserProfileRepository;
import gb.vhs.api.web.UsersController;
import net.minidev.json.JSONObject;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.text.ParseException;
import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.time.ZoneOffset.UTC;

@Service
@EnableScheduling
public class CronJobService {

    @Autowired
    private UsersController usersController;

    @Autowired
    private TimeIntervalRepository timeIntervalRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private GetCurrentPoints getCurrentPoints;

    @Autowired
    private UpdateProfile updateProfile;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "MessageScheduling";

    @Scheduled(cron = "0 0 0/4 * * *") //every 4 hours
//    @Scheduled(cron = "0 0 * * * *") // every hour
//    @Scheduled(cron = "0 0/10 * * * *") // every 10 minutes
//    @Scheduled(cron = "0 * * * * *") // every minutes
//    @Scheduled(cron = "0/10 * * * * *") // every 10 second
//    @Scheduled(cron = "* * * * * *") // every second
    public void checkSchedule() {
        if (LocalTime.now().isAfter(LocalTime.of(8, 59)) && LocalTime.now().isBefore(LocalTime.of(13, 0))) {
            TimeInterval ti = timeIntervalRepository.findByDaySegmentAndDayOfWeek(Enum.DaySegment.MORNING,
                    LocalDate.now().getDayOfWeek());
            List<User> users = ti.getUser();

            if (!CollectionUtils.isEmpty(users)) {
                for (User u : users) {
                    Map<String, String> message = new HashMap<>();

                    List<UserProfile> userProfiles = userProfileRepository.getAllByUser(u);

                    userProfiles.sort(Comparator.comparing(UserProfile::getTimestamp).reversed());
                    UserProfile latest_profile = userProfiles.get(0);

                    List<UserProfile> generalProfiles = userProfileRepository.getAllByIsGeneral(true);
                    generalProfiles.sort(Comparator.comparing(UserProfile::getTimestamp).reversed());
                    UserProfile latestGeneralProfile = generalProfiles.get(0);

                    try{
                        if (u.getPlayerId() != 0) {
                            Map<String, String> pointDict = getCurrentPoints.getCurrentPoints(u);
                            if (pointDict.get("Status").equals("Success")){
                                message.put("teamPoints", pointDict.get("teamPoints"));
                                message.put("userPoints", pointDict.get("userPoints"));
                            }
                        }

                        ObjectWriter writer = new ObjectMapper().writer();

                        String profile_json = writer.writeValueAsString(latest_profile);
                        String general_profile_json = writer.writeValueAsString(latestGeneralProfile);

                        Random random = new Random();
                        Long new_schedule_id = Math.abs(random.nextLong());
                        message.put("schedule_id", new_schedule_id.toString());
                        message.put("profile", profile_json);
                        message.put("general_profile", general_profile_json);

                        String message_json = new JSONObject(message).toJSONString();
                        kafkaTemplate.send(TOPIC, message_json);
                        System.out.println("Sent");

                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (JsonGenerationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (LocalTime.now().isAfter(LocalTime.of(12, 59)) && LocalTime.now().isBefore(LocalTime.of(17, 0))) {
            TimeInterval ti = timeIntervalRepository.findByDaySegmentAndDayOfWeek(Enum.DaySegment.AFTERNOON,
                    LocalDate.now().getDayOfWeek());
            List<User> users = ti.getUser();

            if (!CollectionUtils.isEmpty(users)) {
                for (User u : users) {
                    Map<String, String> message = new HashMap<>();

                    List<UserProfile> userProfiles = userProfileRepository.getAllByUser(u);

                    userProfiles.sort(Comparator.comparing(UserProfile::getTimestamp).reversed());
                    UserProfile latest_profile = userProfiles.get(0);

                    List<UserProfile> generalProfiles = userProfileRepository.getAllByIsGeneral(true);
                    generalProfiles.sort(Comparator.comparing(UserProfile::getTimestamp).reversed());
                    UserProfile latestGeneralProfile = generalProfiles.get(0);

                    try{
                        if (u.getPlayerId() != 0) {
                            Map<String, String> pointDict = getCurrentPoints.getCurrentPoints(u);
                            if (pointDict.get("Status").equals("Success")){
                                message.put("teamPoints", pointDict.get("teamPoints"));
                                message.put("userPoints", pointDict.get("userPoints"));
                            }
                        }

                        ObjectWriter writer = new ObjectMapper().writer();

                        String profile_json = writer.writeValueAsString(latest_profile);
                        String general_profile_json = writer.writeValueAsString(latestGeneralProfile);

                        Random random = new Random();
                        Long new_schedule_id = Math.abs(random.nextLong());
                        message.put("schedule_id", new_schedule_id.toString());
                        message.put("profile", profile_json);
                        message.put("general_profile", general_profile_json);

                        String message_json = new JSONObject(message).toJSONString();
                        kafkaTemplate.send(TOPIC, message_json);
                        System.out.println("Sent");

                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (JsonGenerationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else if (LocalTime.now().isAfter(LocalTime.of(16, 59)) && LocalTime.now().isBefore(LocalTime.of(21, 0))) {
            TimeInterval ti = timeIntervalRepository.findByDaySegmentAndDayOfWeek(Enum.DaySegment.EVENING,
                    LocalDate.now().getDayOfWeek());
            List<User> users = ti.getUser();
            if (!CollectionUtils.isEmpty(users)) {
                for (User u : users) {
                    Map<String, String> message = new HashMap<>();

                    List<UserProfile> userProfiles = userProfileRepository.getAllByUser(u);

                    userProfiles.sort(Comparator.comparing(UserProfile::getTimestamp).reversed());
                    UserProfile latest_profile = userProfiles.get(0);

                    List<UserProfile> generalProfiles = userProfileRepository.getAllByIsGeneral(true);
                    generalProfiles.sort(Comparator.comparing(UserProfile::getTimestamp).reversed());
                    UserProfile latestGeneralProfile = generalProfiles.get(0);

                    try{
                        if (u.getPlayerId() != 0) {
                            Map<String, String> pointDict = getCurrentPoints.getCurrentPoints(u);
                            if (pointDict.get("Status").equals("Success")){
                                message.put("teamPoints", pointDict.get("teamPoints"));
                                message.put("userPoints", pointDict.get("userPoints"));
                            }
                        }

                        ObjectWriter writer = new ObjectMapper().writer();

                        String profile_json = writer.writeValueAsString(latest_profile);
                        String general_profile_json = writer.writeValueAsString(latestGeneralProfile);

                        Random random = new Random();
                        Long new_schedule_id = Math.abs(random.nextLong());
                        message.put("schedule_id", new_schedule_id.toString());
                        message.put("profile", profile_json);
                        message.put("general_profile", general_profile_json);

                        String message_json = new JSONObject(message).toJSONString();
                        kafkaTemplate.send(TOPIC, message_json);
                        System.out.println("Sent");

                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (JsonGenerationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
