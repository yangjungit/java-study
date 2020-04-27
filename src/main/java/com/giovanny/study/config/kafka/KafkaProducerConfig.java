package com.giovanny.study.config.kafka;

import lombok.Setter;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ChenHan  2018/06/11
 * @desc: kafka生产者配置 批量提交
 */
@Setter
@Configuration
@EnableKafka
@ConfigurationProperties(prefix = "spring.kafka.producer")
@Primary
public class KafkaProducerConfig {

    /**
     * 批量提交最大缓存时间 ms
     */
    private String lingerMs;

    @Autowired
    KafkaProperties kafkaProperties;


    private ProducerFactory<String, String> producerFactory() {

        Map<String, Object> properties = new HashMap<>(16);
        properties.putAll(kafkaProperties.buildProducerProperties());
        properties.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        return new DefaultKafkaProducerFactory<>(properties);
    }



    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


}
