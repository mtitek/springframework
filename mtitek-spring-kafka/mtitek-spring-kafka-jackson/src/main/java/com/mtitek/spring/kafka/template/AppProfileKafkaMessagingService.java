package com.mtitek.spring.kafka.template;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.mtitek.spring.model.AppProfile;

@Service
public class AppProfileKafkaMessagingService {
	private KafkaTemplate<String, AppProfile> kafkaTemplate;

	// @Autowired
	public AppProfileKafkaMessagingService(KafkaTemplate<String, AppProfile> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(AppProfile appProfile) {
		kafkaTemplate.send("mtitek-kafka-topic-1", 0, "key0", appProfile);
	}
}