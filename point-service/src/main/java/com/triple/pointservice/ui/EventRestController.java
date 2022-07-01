package com.triple.pointservice.ui;

import com.triple.pointservice.adaptor.PointEventProducer;
import com.triple.pointservice.application.PointEventService;
import com.triple.pointservice.application.dto.PointEventResponse;
import com.triple.pointservice.application.dto.ReviewEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/events")
public class EventRestController {
    private final PointEventService pointEventService;
    private final PointEventProducer pointEventProducer;

    @PostMapping
    public ResponseEntity<List<PointEventResponse>> save(@RequestBody ReviewEventRequest reviewEventRequest) {
        List<PointEventResponse> events = pointEventService.save(reviewEventRequest);
        events.forEach(pointEventProducer::send);
        return ResponseEntity.created(URI.create("/events")).body(events);
    }
}
