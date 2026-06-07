package com.mtitek.spring.kafka.consumer;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtitek.spring.model.AppProfile;

@RestController
@RequestMapping(path = "/api/kafka/consumer/factory/appProfiles", produces = "application/json")
public class AppProfileConsumerFactoryController {
	private final Consumer<String, AppProfile> consumer;
	private final String TOPIC = "mtitek-kafka-topic-2";

	public AppProfileConsumerFactoryController(ConsumerFactory<String, AppProfile> consumerFactory) {
		this.consumer = consumerFactory.createConsumer();
		this.consumer.assign(List.of(new TopicPartition(TOPIC, 0)));
	}

	@GetMapping("/messages")
	public synchronized List<AppProfile> pollMessages() {
		ConsumerRecords<String, AppProfile> records = consumer.poll(Duration.ofSeconds(5));

		Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
		records.partitions()
				.forEach(tp -> offsets.put(tp, new OffsetAndMetadata(records.records(tp).getLast().offset() + 1)));

		consumer.commitSync(offsets);

		return StreamSupport.stream(records.spliterator(), false).map(ConsumerRecord::value).toList();
	}
}