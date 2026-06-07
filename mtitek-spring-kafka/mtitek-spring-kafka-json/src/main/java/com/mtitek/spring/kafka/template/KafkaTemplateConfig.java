package com.mtitek.spring.kafka.template;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.mtitek.spring.model.AppProfile;

@Configuration
public class KafkaTemplateConfig {
	@Bean
	public KafkaTemplate<String, AppProfile> kafkaTemplate(ProducerFactory<String, AppProfile> producerFactory,
			ConsumerFactory<String, AppProfile> consumerFactory) {
		KafkaTemplate<String, AppProfile> kafkaTemplate = new KafkaTemplate<>(producerFactory);
		kafkaTemplate.setConsumerFactory(consumerFactory);
		return kafkaTemplate;
	}
}
