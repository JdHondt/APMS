package gb.vhs.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import gb.vhs.api.entity.enumeration.Enum;
import gb.vhs.api.entity.response.View;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class MessageTemplate {


    @Id
    @Column(name="template_key", length=64)
    @JsonView(View.Base.class)
    private String templateKey;

    @NotNull
    @JsonView(View.Base.class)
    private String content;

    @Enumerated(EnumType.STRING)
    @NotNull
    @JsonView(View.Base.class)
    private Enum.PersuasionProfile persuasionProfile;

    @Enumerated(EnumType.STRING)
    @NotNull
    @JsonView(View.Base.class)
    private Enum.ActivityType activityType;

    @OneToMany(mappedBy = "messageTemplate") // Message Template 1 - * Messages
    @JsonIgnore
    private List<Message> messages;




    public MessageTemplate() {
    }
}
