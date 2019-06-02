package gb.vhs.api.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import gb.vhs.api.entity.*;
import gb.vhs.api.entity.enumeration.Enum;
import gb.vhs.api.entity.response.View;
import gb.vhs.api.repository.*;
import gb.vhs.api.service.*;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@RestController
public class UsersController {

    @Value("${gamebus.url}")
    private String GAMEBUS_BASE_URL;

    @Value("${gamebus.administrator.token}")
    private String TOKEN;

    @Value("${gamebus.administrator.player.id}")
    private String PLAYER_ID;



    @Autowired
    PageRequestService pageRequestService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageTemplateRepository messageTemplateRepository;

    @Autowired
    MessageFeedbackRepository messageFeedbackRepository;

    @Autowired
    TimeIntervalRepository timeIntervalRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    PerformedActivityRepository performedActivityRepository;

    @Autowired
    GetMessageScheduleService getMessageScheduleService;

    @Autowired
    ActivityTriggerAnalyzer activityTriggerAnalyzer;

    @Autowired
    UpdateProfile updateProfile;

    @Autowired
    GetCurrentPoints getCurrentPoints;

    /**
     * GET /users
     */
    @RequestMapping(value = "/users", produces = {"application/json"}, method = RequestMethod.GET)
    public String getUser(
            @RequestParam(value = "email") String email_path
    ) {
        Optional<User> optionalUser = userRepository.findFirstByEmail(email_path);
        if (!optionalUser.isPresent()){
            throw new RuntimeException("user not found in db");
        }
        User user = optionalUser.get();

        ObjectWriter writer = new ObjectMapper().writer();

        try{
        String user_json = writer.writeValueAsString(user);
        return user_json;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failed";
    }

    /**
     * POST /users
     */
    @RequestMapping(value = "/users", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity addUser(
            @RequestParam Long player_id,
            @RequestParam String email,
            @RequestParam String firstName,
            @RequestParam Long teamId,
            @RequestParam(value = "max_messages", defaultValue = "4") Integer max_messages,
            @RequestParam(value = "preferred_day_segment", defaultValue = "AFTERNOON") Enum.DaySegment preferred_day_segment
    ) {
        User u = new User();
        u.setPlayerId(player_id);
        u.setEmail(email);
        u.setFirstName(firstName);
        u.setTeamId(teamId);
        u.setMaxMessagesPerWeek(max_messages);
        u.setPreferredDaySegment(preferred_day_segment);
        u.setUpdates(0);

        Integer pick = new Random().nextInt(Enum.TreatmentGroup.values().length);
        u.setTreatmentGroup(Enum.TreatmentGroup.values()[pick]);
        u.setTimeIntervals(getMessageScheduleService.getMessageSchedule(preferred_day_segment, max_messages));

        UserProfile userProfile = new UserProfile(1,1,
                1,1,
                1,1,
                1,1,
                1,1);

        userRepository.save(u);

        userProfile.setUser(u);
        userProfileRepository.save(userProfile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * PUT /users/{email}
     */
    @RequestMapping(value = "/users/{email}", produces = {"application/json"}, method = RequestMethod.PUT)
    public String updateUser(
            @PathVariable(value = "email") String email_path,
            @RequestParam(value = "email", required = false) String email_param,
            @RequestParam(value = "playerId", required = false) Long player_id,
            @RequestParam(value = "firstName", required = false) String first_name,
            @RequestParam(value = "max_messages", required = false) Integer max_messages,
            @RequestParam(value = "preferred_day_segment", required = false) String preferred_day_segment_string)
    {
        // Confirm that user already exists in database
        Optional<User> optionalUser = userRepository.findFirstByEmail(email_path);
        if(!optionalUser.isPresent()){
            throw new RuntimeException("Cannot find user in db. Please provide a valid identifier");
        }
        User user = optionalUser.get();

        // Update user
        if( max_messages != null) {
            user.setMaxMessagesPerWeek(max_messages);
        }

        if(StringUtils.isNotBlank(preferred_day_segment_string)){
            Enum.DaySegment preferred_day_segment = Enum.DaySegment.valueOf(preferred_day_segment_string);
            user.setPreferredDaySegment(preferred_day_segment);
        }

        if(StringUtils.isNotBlank(first_name)) { user.setFirstName(first_name); }

        if(StringUtils.isNotBlank(email_param)) { user.setFirstName(email_param); }

        Integer current_max_messages = user.getMaxMessagesPerWeek();
        Enum.DaySegment current_preferred_day_segment = user.getPreferredDaySegment();

        user.setTimeIntervals(getMessageScheduleService.getMessageSchedule(current_preferred_day_segment,
                current_max_messages));

        userRepository.save(user);

        return "Saved";
    }

    /**
     * DELETE /users/{email}
     */
    @RequestMapping(value = "/users/{email}", produces = {"application/json"}, method = RequestMethod.DELETE)
    public String updateUser(
            @PathVariable(value = "email") String email_path)
    {
        // Confirm that user already exists in database
        Optional<User> optionalUser = userRepository.findFirstByEmail(email_path);
        if(!optionalUser.isPresent()){
            throw new RuntimeException("Cannot find user in db. Please provide a valid identifier");
        }
        User user = optionalUser.get();

        userRepository.deleteByEmail(user.getEmail());

        return "Saved";
    }


//    /**
//     * POST /users/{id}/profile
//     */
//    @RequestMapping(value = "/users/{id}/profile", produces = {"application/json"}, method = RequestMethod.POST)
//    public ResponseEntity<UserProfile> updateUserProfile(
//            @PathVariable Long id,
//            @RequestParam Double extraversion,
//            @RequestParam Double agreeableness,
//            @RequestParam Double conscientiousness,
//            @RequestParam Double neuroticism,
//            @RequestParam Double openness
//    ) {
//        Optional<User> optionalUser = userRepository.findFirstById(id);
//        if (!optionalUser.isPresent()){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        User user = optionalUser.get();
//        List<UserProfile> old_userProfiles = user.getUserProfiles();
//
//        UserProfile profile = new UserProfile(extraversion, agreeableness,conscientiousness,
//                neuroticism, openness);
//        profile.setUser(user);
//
//        List<UserProfile> new_userProfiles = new ArrayList<>(old_userProfiles);
//        new_userProfiles.add(profile);
//        user.setUserProfiles(new_userProfiles);
//
//        userRepository.save(user);
//        userProfileRepository.save(profile);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    /**
     * POST /users/messagepreferences
     */
    @RequestMapping(value = "/users/messagepreferences", produces = {"application/json"}, method = RequestMethod.POST)
    public String updateUser(
            @RequestParam(value = "schedule_id") Long schedule_id,
            @RequestParam(value = "max_messages", required = false) Integer max_messages,
            @RequestParam(value = "preferred_day_segment", required = false) String preferred_day_segment_string) {


        // Confirm that user already exists in database
        Optional<Message> optionalMessage = messageRepository.findFirstByScheduleId(schedule_id);
        if (!optionalMessage.isPresent()) {
            throw new RuntimeException("Message not found by schedule_id");
        }
        Message message = optionalMessage.get();
        User user = message.getUser();


        if (user != null){
            // Update user
            if( max_messages != null) {
                max_messages = Math.min(Math.max(max_messages, 3), 7);
                user.setMaxMessagesPerWeek(max_messages);
            }

            if(StringUtils.isNotBlank(preferred_day_segment_string)){
                Enum.DaySegment preferred_day_segment = Enum.DaySegment.valueOf(preferred_day_segment_string);
                user.setPreferredDaySegment(preferred_day_segment);
            }
            Integer current_max_messages = user.getMaxMessagesPerWeek();
            Enum.DaySegment current_preferred_day_segment = user.getPreferredDaySegment();

            user.setTimeIntervals(getMessageScheduleService.getMessageSchedule(current_preferred_day_segment,
                    current_max_messages));

            userRepository.save(user);
            System.out.println("Saved feedback");
        }
        return "Saved";
    }

    /**
     * POST /users/{id}/activities
     */
    @RequestMapping(value = "/users/{player_id}/activities", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity postPerformedActivity(
            @PathVariable Long player_id,
            @RequestParam Long activity_id,
            @RequestParam Enum.ActivityTrigger activityTrigger
    ) {
        Optional<User> optionalUser = userRepository.findFirstByPlayerId(player_id);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();

        Activity activity = activityRepository.findFirstById(activity_id);

//        Create PerformedActivity
        PerformedActivity performedActivity = new PerformedActivity();
        performedActivity.setActivity(activity);
        performedActivity.setUser(user);
        performedActivity.setTimestamp(LocalDateTime.now());
        performedActivity.setActivityTrigger(activityTrigger);
        performedActivityRepository.save(performedActivity);

//        Bind to user
        List<PerformedActivity> performed_activities= user.getPerformedActivities();
        performed_activities.add(performedActivity);
        user.setPerformedActivities(performed_activities);
        userRepository.save(user);

//        Bind to activity
        List<PerformedActivity> performed_activities1= activity.getPerformedActivities();
        performed_activities1.add(performedActivity);
        activity.setPerformedActivities(performed_activities1);
        activityRepository.save(activity);

        // Check if profile needs to be updated
        activityTriggerAnalyzer.activityTriggerAnalyzer(performedActivity);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    /**
     * POST /messages
     */
    @RequestMapping(value = "/messages", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity addMessage(
            @RequestParam String message_uri,
            @RequestParam Long schedule_id,
            @RequestParam("sentAt")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime sentAt,
            @RequestParam(value = "readAt", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime readAt,
            @RequestParam String recipient,
            @RequestParam Enum.PersuasionProfile persuasion_profile,
            @RequestParam Long activityId
    ) {
        Message m = new Message();
        m.setMessageURI(message_uri);
        m.setScheduleId(schedule_id);

        m.setSentAt(sentAt);
        m.setReadAt(readAt);

        Optional<MessageTemplate> optionalMessageTemplate = messageTemplateRepository.findFirstByPersuasionProfile(persuasion_profile);

        if(!optionalMessageTemplate.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MessageTemplate messageTemplate = optionalMessageTemplate.get();
        m.setMessageTemplate(messageTemplate);

        Optional<User> optionalUser = userRepository.findFirstByEmail(recipient);

        if (!optionalUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        m.setUser(user);
        List<Message> messages = user.getMessages();
        List<Message> new_messages = new ArrayList<Message>(messages); new_messages.add(m);
        user.setMessages(new_messages);
        userRepository.save(user);

        Activity activity = activityRepository.findFirstById(activityId);
        m.setActivity(activity);

        messageRepository.save(m);



        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * POST /messages/feedback
     */
    @RequestMapping(value = "/messages/feedback", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity logFeedback(
            @RequestParam Long schedule_id,
            @RequestParam Enum.MessageFeedback messageFeedback
    ) {
        MessageFeedback f = new MessageFeedback();
        f.setDate(LocalDateTime.now());
        f.setMessageFeedback(messageFeedback);

        Optional<Message> opt = messageRepository.findFirstByScheduleId(schedule_id);
        opt.ifPresent(message -> {
            f.setMessage(message);

            List<MessageFeedback> mf = message.getFeedback();
            List<MessageFeedback> new_mf = new ArrayList<MessageFeedback>(mf); new_mf.add(f);
            message.setFeedback(new_mf);
            messageRepository.save(message);
        });
        opt.orElseThrow(()  -> new RuntimeException("schedule_id not found in database"));

        messageFeedbackRepository.save(f);

        Enum.PersuasionProfile personalityToUpdate = f.getMessage().getMessageTemplate().getPersuasionProfile();

        if (f.getMessageFeedback() == Enum.MessageFeedback.POSITIVE_YES_ACTION ||
            f.getMessageFeedback() == Enum.MessageFeedback.POSITIVE_NO_ACTION) {

            updateProfile.mainUpdateProfile(f.getMessage().getUser(), 1, personalityToUpdate);
        }else if (f.getMessageFeedback() == Enum.MessageFeedback.NEGATIVE_PERSUASION_PROFILE) {
            updateProfile.mainUpdateProfile(f.getMessage().getUser(), 0, personalityToUpdate);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * GET /message_template/{id}
     */
    @RequestMapping(value = "/message_template/{id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public String getMessageTemplate(
            @PathVariable(value = "id") String templateKey
    ) {
        Optional<MessageTemplate> optionalMessageTemplate = messageTemplateRepository.findFirstByTemplateKey(templateKey);
        if (!optionalMessageTemplate.isPresent()){
            throw new RuntimeException("Cannot find MessageTemplate by TemplateKey in db");
        }

        Map<String, String> result = new HashMap<>();
        ObjectWriter writer = new ObjectMapper().writer();

        MessageTemplate messageTemplate = optionalMessageTemplate.get();
        Activity activity = null;

        if (messageTemplate.getActivityType()==Enum.ActivityType.RANDOM){
            Random random = new Random();
            Integer activity_int = random.nextInt(28);
            Long activity_long = new Long(activity_int);
            activity = activityRepository.findFirstById(activity_long);
        }else {
            activity = activityRepository.findFirstByActivityType(messageTemplate.getActivityType());
        }

        String result_json = new String();
        try{
            String activity_json = writer.writeValueAsString(activity);
            String message_template_json = writer.writeValueAsString(messageTemplate);
            result.put("message_template", message_template_json);
            result.put("activity", activity_json);

            result_json = new JSONObject(result).toJSONString();

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result_json;
    }

    /**
     * GET /test-circle
     */
    @RequestMapping(value = "/test-circle",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity testCircle(
            @RequestParam(value = "q", required = false) String query) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(TOKEN);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        String url = GAMEBUS_BASE_URL + "/circles/11";

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);

        if (response.getStatusCodeValue() != 200) {
            return null;
        }

        System.out.println(response.getBody());
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }



    /**
     * GET /test-activity
     */
    @RequestMapping(value = "/test-activity",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity testActivity(
            @RequestParam(value = "q", required = false) String query) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(TOKEN);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        String url = GAMEBUS_BASE_URL + "/players/" + PLAYER_ID + "/circles-activities";

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);

        if (response.getStatusCodeValue() != 200) {
            return null;
        }

        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }









//
//    /**
//     * POST /oauth/sign-up
//     * Register a new user
//     */
//    @RequestMapping(
//            value = "/users/sign-up",
//            consumes = "application/x-www-form-urlencoded",
//            produces = { "application/json" },
//            method = RequestMethod.POST)
//    public ResponseEntity signUp(
//            @ModelAttribute(value = "email") @Email String email,
//            @ModelAttribute(value = "password") String password,
//            @ModelAttribute(value = "firstName") String firstName,
//            @ModelAttribute(value = "lastName") String lastName,
//            @ModelAttribute(value = "language") String language) {
//
//        // User already exists
//        if (userRepository.findFirstByEmail(email) != null) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }

        // Create a new user with standard role
//        User user = new User(email, passwordEncoder.encode(password), firstName, lastName, StringUtils.defaultIfBlank(language, DEFAULT_LANGUAGE));
//
//        user.setActivationToken(Base64.getUrlEncoder().withoutPadding().encodeToString( (new Date().getTime() + RandomStringUtils.randomAlphanumeric(8)).getBytes() ));
//        user.setActivationExpiryDate( LocalDateTime.now(ZoneOffset.UTC).plus(7, DAYS) );
//
//        user.setLegalTerms( legalTermsRepository.findFirstByOrderByEffectiveDateDesc() );
//        user.setLegalTermsAcceptedDate( LocalDateTime.now(ZoneOffset.UTC) );
//
//        List<UserRole> userRoles = Arrays.asList( new UserRole("USER") );
//        user.setUserRoles(userRoles);

//        userRepository.save(user);

        // Sent welcome email with email verification link
        //String path = EMAIL_VERIFICATION_PATH + "/" + user.getEmail() + "/" + user.getActivationToken();
        //emailService.sentEmail(email, "Welcome to GameBus: Register your email", "<h3>Welcome to GameBus!</h3><p>Confirm your email <b>within 7 days</b> by clicking: <a href='" + path + "'>verify account</a>.<br>If the link does not work, copy " + path + " into your browser.<p>");

//        return new ResponseEntity<>(user, HttpStatus.CREATED);
//        return null;
//    }



    /**
     * GET /users/{userId}
     */
    @JsonView(View.User.class)
    @RequestMapping(value = "/user",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity authUserGet() {
        Long user = 1L;
        if (user == null) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        return this.userGet(user);
    }
    @JsonView(View.User.class)
    @RequestMapping(value = "/users/{userId}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity userGet(
            @PathVariable("userId") Long userId) {

        User user = userRepository.getOne(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }









}
