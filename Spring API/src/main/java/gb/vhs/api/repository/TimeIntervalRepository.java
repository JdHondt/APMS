package gb.vhs.api.repository;


import gb.vhs.api.entity.TimeInterval;
import gb.vhs.api.entity.User;
import gb.vhs.api.entity.enumeration.Enum;
import gb.vhs.api.repository.implementation.CustomRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeIntervalRepository extends CustomRepository<TimeInterval, Long> {

    TimeInterval findByDaySegmentAndDayOfWeek(Enum.DaySegment day_segment, DayOfWeek dayOfWeek);

}
