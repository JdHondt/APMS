package gb.vhs.api.entity;

import com.fasterxml.jackson.annotation.JsonView;
import gb.vhs.api.entity.enumeration.Enum;
import gb.vhs.api.entity.response.View;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
@Data
public class MessageFeedback {

    @Id
    @GeneratedValue
    @JsonView(View.Base.class)
    private Long id;

    @NotNull
    @JsonView(View.Base.class)
    private LocalDateTime date;


    @Enumerated(EnumType.STRING)
    @NotNull
    @JsonView(View.Base.class)
    private Enum.MessageFeedback messageFeedback;


    @ManyToOne(optional = false)
    @JoinColumn
    @JsonView(View.F_M.class)  // MessageFeedback * - 1 Message
    private Message message;






    public MessageFeedback() {
    }
}
