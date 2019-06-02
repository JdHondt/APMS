package gb.vhs.api.repository;


import gb.vhs.api.entity.Message;
import gb.vhs.api.entity.User;
import gb.vhs.api.repository.implementation.CustomRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends CustomRepository<Message, Long> {

    Optional<Message> findFirstByScheduleId(Long schedule_id);
}


