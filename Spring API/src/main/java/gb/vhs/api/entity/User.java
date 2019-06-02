package gb.vhs.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import gb.vhs.api.entity.enumeration.Enum;
import gb.vhs.api.entity.response.View;
import gb.vhs.api.repository.TimeIntervalRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;


@Entity
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User {

    @Id
    @GeneratedValue
    @JsonView(View.Base.class)
    private Long id;

    @JsonView(View.Base.class)
    private Long playerId;

    @JsonView(View.Base.class)
    private Long teamId;

    @Email
    @Column(nullable = false, length = 250)
    @JsonView(View.UserExt.class)
    private String email;

    @JsonView(View.Base.class)
    private String firstName;

    @JsonView(View.Base.class)
    private Integer maxMessagesPerWeek;

    @Enumerated(EnumType.STRING)
    @JsonView(View.Base.class)
    private Enum.DaySegment preferredDaySegment;

    @Enumerated(EnumType.STRING)
    @JsonView(View.Base.class)
    private Enum.TreatmentGroup treatmentGroup;

    @JsonView(View.Base.class)
    private Integer updates;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)// User 1 - * Messages
    @JsonView(View.U_M.class)
    private List<Message> messages;

    @OneToMany(mappedBy = "user")// User 1 - * UserProfile
    @JsonIgnore
    private List<UserProfile> userProfiles;

    @OneToMany(mappedBy = "user")// User 1 - * PerformedActivities
    @JsonIgnore
    private List<PerformedActivity> performedActivities;

    @ManyToMany // User * - * Time Intervals
    @JoinTable
    @JsonIgnore
    private List<TimeInterval> timeIntervals;




    public User() {
    }
}