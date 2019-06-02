package gb.vhs.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import gb.vhs.api.entity.response.View;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Message {


    @Id
    @GeneratedValue
    @JsonView(View.Base.class)
    private Long id;

    @NotNull
    @Column(name="message_uri")
    @JsonView(View.Base.class)
    private String messageURI;

    @NotNull
    @JsonView(View.Base.class)
    private Long scheduleId;

    @NotNull
    @JsonView(View.Base.class)
    private LocalDateTime sentAt;

    @JsonView(View.Base.class)
    private LocalDateTime readAt;

    @ManyToOne(optional = false) // Messages * - 1 User
    @JoinColumn
    @JsonIgnore
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn
    @JsonView(View.M_MT.class)  // Messages * - 1 Message Template
    private MessageTemplate messageTemplate;

    @ManyToOne // Messages * - 1 Activity
    @JoinColumn
    @JsonIgnore
    private Activity activity;

    @OneToMany(mappedBy = "message") // Message 1 - * MessageFeedback
    @JsonIgnore
    private List<MessageFeedback> feedback;


    public Message() {
    }
}
