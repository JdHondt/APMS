package gb.vhs.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import gb.vhs.api.entity.enumeration.Enum;
import gb.vhs.api.entity.response.View;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Activity {


    @Id
    @GeneratedValue
    @JsonView(View.Base.class)
    private Long id;

    @NotNull
    @Column(name="gamebus_id")
    @JsonView(View.Base.class)
    private Long gamebusId;

    @NotNull
    @JsonView(View.Base.class)
    private String challengeRuleName;

    @Enumerated(EnumType.STRING)
    @NotNull
    @JsonView(View.Base.class)
    private Enum.ActivityType activityType;

    @OneToMany(mappedBy = "activity")
    @JsonIgnore
    private List<PerformedActivity> performedActivities;

    @OneToMany(mappedBy = "activity")
    @JsonIgnore
    private List<Message> messages;


    public Activity() {
    }
}
