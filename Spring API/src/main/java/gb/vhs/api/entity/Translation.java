package gb.vhs.api.entity;

import com.fasterxml.jackson.annotation.JsonView;
import gb.vhs.api.entity.response.View;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Translation {

    @Id
    @GeneratedValue
    private Long id;

    @JsonView(View.Base.class)
    private String language;

    @JsonView(View.Base.class)
    private String value;

    public Translation() {
    }

    public Translation(String language, String value) {
        this.language = language;
        this.value = value;
    }
}
