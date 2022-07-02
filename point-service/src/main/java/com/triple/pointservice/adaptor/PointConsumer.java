package com.triple.pointservice.adaptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.pointservice.domain.PointEarnService;
import com.triple.pointservice.domain.event.PointEventAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointConsumer {
    private static final String TOPIC = "point-topic";
    private final PointEarnService pointEarnService;
    private KafkaConsumer<String, String> kafkaConsumer;

    @KafkaListener(topics = TOPIC)
    public void updatePoint(String message) {
        ObjectMapper mapper = new ObjectMapper();
        Map<Object, Object> map = new HashMap<>();
        try {
            map = mapper.readValue(message, new TypeReference<>() {
            });
            log.info("Consumed message in {} : {}", TOPIC, message);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        UUID userId = UUID.fromString((String) map.get("userId"));
        String action = (String) map.get("action");
        if (PointEventAction.valueOf(action) == PointEventAction.ADD) {
            pointEarnService.addPoint(userId);
        }
        if (PointEventAction.valueOf(action) == PointEventAction.DELETE) {
            pointEarnService.subPoint(userId);
        }
    }
}
