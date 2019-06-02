package gb.vhs.api.entity;

import com.fasterxml.jackson.annotation.JsonView;
import gb.vhs.api.entity.enumeration.Enum;
import gb.vhs.api.entity.response.View;
import lombok.Data;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"dayOfWeek", "daySegment"})})
public class TimeInterval {

    @Id
    @GeneratedValue
    @JsonView(View.Base.class)
    private Long id;


    @NotNull
    @JsonView(View.Base.class)
    private LocalTime fromDate;

    @NotNull
    @JsonView(View.Base.class)
    private LocalTime toDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    @NotNull
    @JsonView(View.Base.class)
    private DayOfWeek dayOfWeek;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    @NotNull
    @JsonView(View.Base.class)
    private Enum.DaySegment daySegment;

    @ManyToMany(mappedBy = "timeIntervals", fetch = FetchType.EAGER)
    @JsonView(View.T_U.class)  // Property Schemes * - * Activity Schemes
    private List<User> user;



    public TimeInterval() {
    }

    public TimeInterval(List<User> user, @NotNull DayOfWeek dayOfWeek, @NotNull Enum.DaySegment daySegment) {
        this.user = user;
        this.dayOfWeek = dayOfWeek;
        this.daySegment = daySegment;
    }
}
