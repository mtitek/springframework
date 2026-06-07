package com.mtitek.spring.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.mtitek.spring.model.AppProfile;

@Service
public class KafkaListenerConsumerService {
//    @KafkaListener(topics = "mtitek-kafka-topic-1", groupId = "mtitek-kafka-consumer-group-id-c")
//    public void listen (String message) {
//        System.out.println("Received Message: " + message);
//    }

//	// need consumer property: value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
//	@KafkaListener(topics = "mtitek-kafka-topic-1", groupId = "mtitek-kafka-consumer-group-id-c")
//	public void listen (AppProfile appProfile) {
//		System.out.println("Received Message: " + appProfile);
//	}
	
	@KafkaListener(topics = "mtitek-kafka-topic-1", groupId = "mtitek-kafka-consumer-group-id-c")
	public void listen (AppProfile appProfile, ConsumerRecord<String, AppProfile> consumerRecord) {
		System.out.println("Received Message: " + appProfile + " - " + consumerRecord);
		// Received Message: AppProfile(id=2, name=profile2, role=ADMIN) - ConsumerRecord(topic = mtitek-kafka-topic-1, partition = 0, leaderEpoch = 0, offset = 14, CreateTime = 1778272692563, deliveryCount = null, serialized key size = -1, serialized value size = 43, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = AppProfile(id=2, name=profile2, role=ADMIN))
	}	

//	@KafkaListener(topics = "mtitek-kafka-topic-1", groupId = "mtitek-kafka-consumer-group-id-c")
//	public void listen (AppProfile appProfile, Message<AppProfile> message) {
//		System.out.println("Received Message: " + appProfile + " - " + message);
//		// Received Message: AppProfile(id=2, name=profile2, role=ADMIN) - GenericMessage [payload=AppProfile(id=2, name=profile2, role=ADMIN), headers={kafka_offset=13, kafka_consumer=org.springframework.kafka.core.DefaultKafkaConsumerFactory$ExtendedKafkaConsumer@4e6f101, kafka_timestampType=CREATE_TIME, kafka_receivedPartitionId=0, kafka_receivedTopic=mtitek-kafka-topic-1, kafka_receivedTimestamp=1778272604734, kafka_groupId=mtitek-kafka-consumer-group-id-c}]
//	}
}
