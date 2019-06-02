package gb.vhs.api.repository;


import gb.vhs.api.entity.Activity;
import gb.vhs.api.entity.PerformedActivity;
import gb.vhs.api.repository.implementation.CustomRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerformedActivityRepository extends CustomRepository<PerformedActivity, Long> {


}


