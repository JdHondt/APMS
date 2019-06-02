package gb.vhs.api.entity;

import com.fasterxml.jackson.annotation.JsonView;
import gb.vhs.api.entity.response.View;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Data
public class UserProfile {

    @Id
    @GeneratedValue
    @JsonView(View.Base.class)
    private Long id;

    @JsonView(View.Base.class)
    private Boolean isGeneral;

    @JsonView(View.Base.class)
    private LocalDateTime timestamp;

    @JsonView(View.Base.class)
    private Integer extraversion_A;

    @JsonView(View.Base.class)
    private Integer extraversion_B;

    @JsonView(View.Base.class)
    private Integer agreeableness_A;

    @JsonView(View.Base.class)
    private Integer agreeableness_B;

    @JsonView(View.Base.class)
    private Integer conscientiousness_A;

    @JsonView(View.Base.class)
    private Integer conscientiousness_B;

    @JsonView(View.Base.class)
    private Integer neuroticism_A;

    @JsonView(View.Base.class)
    private Integer neuroticism_B;

    @JsonView(View.Base.class)
    private Integer openness_A;

    @JsonView(View.Base.class)
    private Integer openness_B;

    @ManyToOne(optional = true)
    @JsonView(View.P_U.class)  // UserProfile * - 1 User
    private User user;

    public UserProfile(){

    }

    public UserProfile(int extraversion_A, int extraversion_B,
                       int agreeableness_A, int agreeableness_B,
                       int conscientiousness_A, int conscientiousness_B,
                       int neuroticism_A, int neuroticism_B,
                       int opennessA, int openness_B) {
        this.isGeneral = false;
        this.timestamp = LocalDateTime.now();
        this.extraversion_A = extraversion_A;
        this.extraversion_B = extraversion_B;
        this.agreeableness_A = agreeableness_A;
        this.agreeableness_B = agreeableness_B;
        this.conscientiousness_A = conscientiousness_A;
        this.conscientiousness_B = conscientiousness_B;
        this.neuroticism_A = neuroticism_A;
        this.neuroticism_B = neuroticism_B;
        this.openness_A = opennessA;
        this.openness_B = openness_B;
    }
}
