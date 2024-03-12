package com.example.kafka.consumer;

import com.example.kafka.common.NotificationMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, NotificationMessage> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_1");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class);

        JsonDeserializer<NotificationMessage> deserializer = new JsonDeserializer<>(NotificationMessage.class,false);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(config,
                new StringDeserializer(),
                deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, NotificationMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}