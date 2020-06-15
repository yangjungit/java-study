package com.giovanny.study.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @packageName: com.giovanny.study.config.kafka
 * @className: KafkaConsumerConfig
 * @description: KafkaConsumerConfig
 * @author: YangJun
 * @date: 2020/4/27 16:00
 * @version: v1.0
 **/
@Configuration
@EnableKafka
@Slf4j
@Order
public class KafkaConsumerConfig {

    @Autowired
    KafkaProperties kafkaProperties;

    private Map<String, Object> consumerConfigs() {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.putAll(kafkaProperties.buildConsumerProperties());
        return consumerProps;
    }

    private ConsumerFactory<String, String> manualConsumerFactory() {
        Map<String, Object> configs = consumerConfigs();
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return new DefaultKafkaConsumerFactory<>(configs);
    }

    private ConsumerFactory<String, String> autoConsumerFactory() {
        Map<String, Object> configs = consumerConfigs();
        return new DefaultKafkaConsumerFactory<>(configs);
    }

    /**
     * 手动提交单条消费
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaManualAckFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(manualConsumerFactory());
        ContainerProperties props = factory.getContainerProperties();
//		props.setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE);
        props.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        props.setIdleEventInterval(100L);
        factory.setAckDiscarded(true);
//        factory.setConcurrency(3);

        return factory;
    }

    /**
     * 自动提交批量消费
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaAutoAckBatchFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(autoConsumerFactory());
        factory.setBatchListener(true);
        ContainerProperties props = factory.getContainerProperties();
        props.setAckMode(ContainerProperties.AckMode.BATCH);
        props.setIdleEventInterval(100L);
        factory.setAckDiscarded(true);

        return factory;
    }
}
