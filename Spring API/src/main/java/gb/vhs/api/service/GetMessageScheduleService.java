package gb.vhs.api.service;

import gb.vhs.api.entity.TimeInterval;
import gb.vhs.api.entity.enumeration.Enum;
import gb.vhs.api.repository.TimeIntervalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GetMessageScheduleService {

    @Autowired
    TimeIntervalRepository timeIntervalRepository;

    public List<TimeInterval> getMessageSchedule(Enum.DaySegment preferred_day_segment, Integer max_messages){
        List<TimeInterval> timeIntervals = new ArrayList<>();

        if (max_messages == 7) {
            // 7 messages = one each day of the week
            timeIntervals.addAll(
                    Arrays.asList(
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.MONDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.TUESDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.WEDNESDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.THURSDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.FRIDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.SATURDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.SUNDAY)
                    )
            );
        }
        else if (max_messages == 6) {
            // 6 messages = monday, tuesday, wednesday, friday, saturday, sunday
            timeIntervals.addAll(
                    Arrays.asList(
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.MONDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.TUESDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.WEDNESDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.FRIDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.SATURDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.SUNDAY)
                    )
            );
        }
        else if (max_messages == 5) {
            // 5 messages = monday, tuesday, thursday, friday, sunday
            timeIntervals.addAll(
                    Arrays.asList(
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.MONDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.TUESDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.THURSDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.FRIDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.SUNDAY)
                    )
            );

        }
        else if (max_messages == 4) {
            // 4 messages = monday, wednesday, friday, sunday
            timeIntervals.addAll(
                    Arrays.asList(
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.MONDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.WEDNESDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.FRIDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.SUNDAY)
                    )
            );
        }
        else if (max_messages == 3){
            // Minimum = 3 messages
            // 3 messages = monday, wednesday, friday
            timeIntervals.addAll(
                    Arrays.asList(
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.MONDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.WEDNESDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.FRIDAY)
                    )
            );
        }
        else if (max_messages == 2){
            timeIntervals.addAll(
                    Arrays.asList(
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.MONDAY),
                            timeIntervalRepository.findByDaySegmentAndDayOfWeek(preferred_day_segment, DayOfWeek.THURSDAY)
                    )
            );
        }else {
            timeIntervals.addAll(
                    Arrays.asList(
                    )
            );
        }
        
        return timeIntervals;
    }
}
