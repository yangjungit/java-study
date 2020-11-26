package com.giovanny.study.controller;

import com.alibaba.fastjson.JSON;
import com.giovanny.study.entity.MyResponse;
import com.giovanny.study.entity.po.Uuu;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @packageName: com.giovanny.study.controller
 * @className: KafkaController
 * @description: KafkaController
 * @author: YangJun
 * @date: 2020/4/27 16:16
 * @version: v1.0
 **/
@RestController
@Slf4j
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/kafka/send")
    public MyResponse sendKafkaMsg() {
        Uuu uuu = new Uuu();
        uuu.setId(1);
        uuu.setName("name");
        uuu.setDescription("desc");
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("giovanny_topic", JSON.toJSONString(uuu));
        return MyResponse.success();
    }

    @KafkaListener(topics = {"giovanny_topic"}, containerFactory = "kafkaManualAckFactory")
    public void kafkaListener(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info("record:[{}]", record);
        ack.acknowledge();
    }
}
