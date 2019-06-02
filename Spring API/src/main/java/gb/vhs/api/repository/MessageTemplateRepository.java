package gb.vhs.api.repository;


import gb.vhs.api.entity.Message;
import gb.vhs.api.entity.MessageTemplate;
import gb.vhs.api.entity.enumeration.Enum;
import gb.vhs.api.repository.implementation.CustomRepository;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageTemplateRepository extends CustomRepository<MessageTemplate, Long> {

    Optional<MessageTemplate> findFirstByPersuasionProfile(Enum.PersuasionProfile persuasionProfile);
    Optional<MessageTemplate> findFirstByTemplateKey(String templateKey);

}


