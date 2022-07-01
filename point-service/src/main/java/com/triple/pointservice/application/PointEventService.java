package com.triple.pointservice.application;

import com.triple.pointservice.application.dto.PointEventResponse;
import com.triple.pointservice.application.dto.ReviewEventRequest;
import com.triple.pointservice.domain.PointEventCalculator;
import com.triple.pointservice.domain.PointCalculatorFactory;
import com.triple.pointservice.domain.PointPolicy;
import com.triple.pointservice.domain.event.PointEvent;
import com.triple.pointservice.domain.event.PointEvents;
import com.triple.pointservice.domain.repository.PointEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class PointEventService {
    private final PointEventRepository pointEventRepository;
    private final PointPolicy pointPolicy;

    public List<PointEventResponse> save(ReviewEventRequest reviewEventRequest) {
        PointEvents pointEvents = calculatePoint(reviewEventRequest);

        List<PointEvent> savePointEvents = new ArrayList<>();
        pointEvents.getPointEvents().forEach(p -> {
            PointEvent saveEvent = pointEventRepository.save(p);
            savePointEvents.add(saveEvent);
        });

        return savePointEvents.stream()
                .map(PointEventResponse::of)
                .collect(Collectors.toList());
    }

    private PointEvents calculatePoint(ReviewEventRequest reviewEventRequest) {
        PointEventCalculator calculator = PointCalculatorFactory.of(reviewEventRequest.getAction());
        PointEvents savedPointEvents =
                new PointEvents(pointEventRepository.findByReviewId(reviewEventRequest.getReviewId()));
        PointEvents savedPlacePointEvents =
                new PointEvents(pointEventRepository.findByPlaceId(reviewEventRequest.getPlaceId()));

        return calculator.calculate(reviewEventRequest.toReview(), pointPolicy, savedPointEvents, savedPlacePointEvents);
    }
}
