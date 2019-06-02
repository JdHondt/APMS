package gb.vhs.api.repository;


import gb.vhs.api.entity.Message;
import gb.vhs.api.entity.MessageFeedback;
import gb.vhs.api.repository.implementation.CustomRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageFeedbackRepository extends CustomRepository<MessageFeedback, Long> {

}


