package com.mtitek.spring.kafka.producer;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtitek.spring.model.AppProfile;

@RestController
@RequestMapping(path = "/api/kafka/producer/factory/appProfiles", produces = "application/json")
public class AppProfileProducerFactoryController {
	private final KafkaTemplate<String, AppProfile> kafkaTemplate;
	private final String TOPIC = "mtitek-kafka-topic-2";

	// @Autowired
	public AppProfileProducerFactoryController(KafkaTemplate<String, AppProfile> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@PostMapping("/messages")
	public ResponseEntity<AppProfile> sendMessage(@RequestBody AppProfile appProfile) {
		kafkaTemplate.send(TOPIC, 0, "key1", appProfile);
		return ResponseEntity.accepted().body(appProfile);
	}

	@PostMapping("/messages/batch")
	public ResponseEntity<List<AppProfile>> sendMessages(@RequestBody List<AppProfile> appProfiles) {
		appProfiles.forEach(appProfile -> kafkaTemplate.send(TOPIC, 0, "key2", appProfile));
		return ResponseEntity.accepted().body(appProfiles);
	}
}