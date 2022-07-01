package com.triple.pointservice.adaptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.pointservice.application.dto.PointEventResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointEventProducer {
    private static final String TOPIC = "point-topic";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public PointEventResponse send(PointEventResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(response);
        } catch(JsonProcessingException ex) {
            ex.printStackTrace();
        }

        kafkaTemplate.send(TOPIC, jsonInString);
        log.info("Send message in {} : {}", TOPIC, jsonInString);
        return response;
    }
}
