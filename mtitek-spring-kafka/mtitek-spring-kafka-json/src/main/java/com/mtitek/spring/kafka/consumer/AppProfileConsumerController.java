package com.mtitek.spring.kafka.consumer;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.StreamSupport;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtitek.spring.model.AppProfile;

@RestController
@RequestMapping(path = "/api/kafka/consumer/appProfiles", produces = "application/json")
public class AppProfileConsumerController {
    private final Consumer<String, AppProfile> consumer;
    private final String TOPIC = "mtitek-kafka-topic-1";
    private final int N = 10;

    public AppProfileConsumerController() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "mtitek-kafka-consumer-group-id-a");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "com.mtitek.spring.model");
        properties.put(JsonDeserializer.VALUE_DEFAULT_TYPE, AppProfile.class);
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, N);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        this.consumer = new KafkaConsumer<>(properties);
        this.consumer.assign(List.of(new TopicPartition(TOPIC, 0)));
    }

    @GetMapping("/messages")
    public synchronized List<AppProfile> pollMessages() {
        ConsumerRecords<String, AppProfile> records = consumer.poll(Duration.ofSeconds(5));

        Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
        records.partitions().forEach(tp ->
            offsets.put(tp, new OffsetAndMetadata(records.records(tp).getLast().offset() + 1))
        );

        consumer.commitSync(offsets);

        return StreamSupport.stream(records.spliterator(), false)
                .map(ConsumerRecord::value)
                .toList();
    }
}