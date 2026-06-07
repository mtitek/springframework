package com.mtitek.spring.kafka.template;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mtitek.spring.model.AppProfile;

@RestController
@RequestMapping(path = "/api/kafka/template/appProfiles", produces = "application/json")
public class AppProfileKafkaTemplateController {
	private AppProfileKafkaMessagingService appProfileKafkaMessagingService;

//	@Autowired
	private KafkaTemplate<String, AppProfile> kafkaTemplate;

	public AppProfileKafkaTemplateController(AppProfileKafkaMessagingService appProfileKafkaMessagingService,
			KafkaTemplate<String, AppProfile> kafkaTemplate) {
		this.appProfileKafkaMessagingService = appProfileKafkaMessagingService;
		this.kafkaTemplate = kafkaTemplate;
	}

	@PostMapping(value = "/messages", consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public AppProfile sendMessage(@RequestBody AppProfile appProfile) {
		appProfileKafkaMessagingService.sendMessage(appProfile);
		return appProfile;
	}

	@GetMapping("/messages/{topic}/{partition}/{offset}")
	public String receiveMessage(@PathVariable String topic, @PathVariable int partition, @PathVariable long offset) {
		System.out.println("messages params: " + topic + " - " + partition);
		ConsumerRecord<String, AppProfile> record = kafkaTemplate.receive(topic, partition, offset);
		return record != null ? record.value().toString() : "No message found";
	}
}
