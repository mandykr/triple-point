package com.triple.pointservice.application;

import com.triple.pointservice.application.dto.PointEventResponse;
import com.triple.pointservice.domain.event.PointEventAction;
import com.triple.pointservice.domain.event.PointEventType;

import java.util.List;
import java.util.stream.Collectors;

public class PointEventResponseFixture {
    public static List<PointEventType> getTypes(List<PointEventResponse> responses) {
        return responses.stream()
                .map(PointEventResponse::getType)
                .collect(Collectors.toList());
    }

    public static List<PointEventAction> getActions(List<PointEventResponse> responses) {
        return responses.stream()
                .map(PointEventResponse::getAction)
                .collect(Collectors.toList());
    }
}
