package gb.vhs.api.entity;

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
public class PerformedActivity {


    @Id
    @GeneratedValue
    @JsonView(View.Base.class)
    private Long id;

    @NotNull
    @JsonView(View.Base.class)
    private LocalDateTime timestamp;

    @NotNull
    @JsonView(View.Base.class)
    private Enum.ActivityTrigger activityTrigger;

    @ManyToOne(optional = false)
    @JoinColumn
    @JsonView(View.PA_U.class)  // PerformedActivity * - 1 User
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn
    @JsonView(View.PA_A.class)  // PerformedActivity * - 1 Activity
    private Activity activity;


    public PerformedActivity() {
    }
}
