package com.datn.drugstore.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegistrationProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String TOPIC = "user-registration";

    public void sendRegistrationEvent(String email, String name) {
        try {
            Map<String, String> data = new HashMap<>();
            data.put("email", email);
            data.put("name", name);

            String message = objectMapper.writeValueAsString(data);
            kafkaTemplate.send(TOPIC, message);

            System.out.println("Sent registration event to Kafka: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
