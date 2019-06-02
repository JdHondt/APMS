package gb.vhs.api.service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.JsonObject;
import gb.vhs.api.entity.User;
import gb.vhs.api.entity.UserProfile;
import gb.vhs.api.entity.enumeration.Enum;
import gb.vhs.api.repository.UserProfileRepository;
import gb.vhs.api.repository.UserRepository;
import net.minidev.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.kafka.common.protocol.types.Field;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.web.JsonPath;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class GetCurrentPoints {

    @Autowired
    GetCurrentTeamChallenge getCurrentTeamChallenge;

    @Value("${gamebus.url}")
    private String GAMEBUS_BASE_URL;

    @Value("${gamebus.administrator.token}")
    private String TOKEN;


    public Map<String, String> getCurrentPoints(User user){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(TOKEN);

        Integer current_challenge = 212;

        Long team_id = user.getTeamId();

        Long player_id = user.getPlayerId();

        String url = GAMEBUS_BASE_URL + "/challenges/" + current_challenge.toString()
                + "/leaderboard/" +  team_id.toString() + "/";

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);

        if (response.getStatusCodeValue() != 200) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> successresp = new HashMap<>();
        successresp.put("Status", "Success");

        try {

            JsonNode root = mapper.readTree(response.getBody());
            String teamPoints = root.at("/points").asText();
            successresp.put("teamPoints", teamPoints);

            JsonNode memberships = root.at("/circle/memberships");

            String userPoints = null;
            for (JsonNode player : memberships){
                String test = player.at("/player/id").asText();
                String test2 = user.getPlayerId().toString();

                if (player.at("/player/id").asText().equals(user.getPlayerId().toString())){
                    userPoints = player.at("/points").asText();
                    break;
                }
            }

            successresp.put("userPoints", userPoints);
            return successresp;

        }catch (IOException e){
            e.printStackTrace();
        }

        Map<String, String> failresp = new HashMap<>();
        failresp.put("Status", "Failed");
        return failresp;
    }
}
