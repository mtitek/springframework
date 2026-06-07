package com.mtitek.spring.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import com.mtitek.spring.model.AppProfile;

@Configuration
public class KafkaConfig {
    @Bean
    public ProducerFactory<String, AppProfile> appProfileProducerFactory() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
        
        configurations.put("com.mtitek.spring.kafka.producerfactory", "producerfactory1");

        return new DefaultKafkaProducerFactory<>(configurations);
    }	
	
	@Bean
	public KafkaTemplate<String, AppProfile> kafkaTemplate(ProducerFactory<String, AppProfile> producerFactory,
			ConsumerFactory<String, AppProfile> consumerFactory) {
		KafkaTemplate<String, AppProfile> kafkaTemplate = new KafkaTemplate<>(producerFactory);

		kafkaTemplate.setConsumerFactory(consumerFactory);

		return kafkaTemplate;
	}

	@Bean
	public ConsumerFactory<String, AppProfile> appProfileConsumerFactory() {
		Map<String, Object> configurations = new HashMap<>();
		configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configurations.put(ConsumerConfig.GROUP_ID_CONFIG, "mtitek-kafka-consumer-group-id-b");
		configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
		configurations.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "com.mtitek.spring.model");
		configurations.put(JacksonJsonDeserializer.VALUE_DEFAULT_TYPE, AppProfile.class);
		configurations.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
		configurations.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		configurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
		configurations.put("com.mtitek.spring.kafka.consumerfactory", "consumerfactory1");

		return new DefaultKafkaConsumerFactory<>(configurations);
	}
}
