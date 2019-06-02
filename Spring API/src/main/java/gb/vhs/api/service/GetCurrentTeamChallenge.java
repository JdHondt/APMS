package gb.vhs.api.service;

import gb.vhs.api.entity.User;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class GetCurrentTeamChallenge {

    public Long getCurrentTeamChallenge(){
        return 191 + ChronoUnit.WEEKS.between(LocalDate.of(2019,4,22), LocalDate.now());
    }
}
