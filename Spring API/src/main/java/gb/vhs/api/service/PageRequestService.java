package gb.vhs.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PageRequestService {

    @Value("${ft.response.limit}")
    private Integer MAX_LIMIT;

    public PageRequest createPageRequest(Integer page, Integer limit, List<String> sortParameters) {

        if (page == null) { page = 0; }
        if (limit == null || limit == 0 || limit > MAX_LIMIT) { limit = MAX_LIMIT; }
        PageRequest pageRequest = PageRequest.of(page, limit);


        // Translate sort parameters into a Sort object
        if (sortParameters != null) {
            List<Sort.Order> sortOrders = new ArrayList<>();
            Sort.Direction direction;
            String property;
            for (String sortParameter: sortParameters) {
                direction = Sort.Direction.ASC;
                property = sortParameter;
                if (sortParameter.substring(0, 1).equals("-")) {
                    direction = Sort.Direction.DESC;
                    property = sortParameter.substring(1);
                }
                sortOrders.add( new Sort.Order(direction, property) );
            }

            pageRequest = PageRequest.of(page, limit, Sort.by(sortOrders));
        }

        return pageRequest;
    }
}
