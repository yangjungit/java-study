package com.giovanny.study.config.rabbitmq;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @packageName: com.example.demo1.config.rabbitmq
 * @className: RabbitMQConfig
 * @description: rabbitmq 配置
 * @author: YangJun
 * @date: 2020/4/10 17:20
 * @version: v1.0
 **/
@Configuration
@Slf4j
@Data
public class RabbitMqConfig {
    private RabbitAdmin rabbitAdmin;

    CachingConnectionFactory myConnectionFactory;

    @Autowired
    public RabbitMqConfig(CachingConnectionFactory myConnectionFactory) {
        this.myConnectionFactory = myConnectionFactory;
    }

    @Value("${spring.rabbitmq.direct-queue-name}")
    private String queueName;

    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(myConnectionFactory);
        //exchange需要手动初始化（要设置参数），然后手动提供 binding 信息
        this.rabbitAdmin = rabbitAdmin;
        return rabbitAdmin;
    }


    @Bean
    public Queue directQueue() {
        // 持久化默认为true
        return new Queue(queueName);
    }

    /**
     * new DirectExchange("exDirect", true, true)
     * 第二个参数持久化 遇到一个问题:
     * 开始，创建没有问题，过了很久，不知道什么原因，我再次重启次项目，发现不能创建队列了，
     * Caused by: com.rabbitmq.client.ShutdownSignalException: channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no queue 'directQueue' in vhost '/', class-id=50, method-id=10)
     * 过程：rabbit mq管理界面查看，再查看rabbit mq日志
     * operation exchange.declare caused a channel exception precondition_failed: inequivalent arg 'durable' for exchange 'exDirect' in vhost '/': received 'false' but current is 'true'
     * 最后在rabbit mq日志中发现 Queue 的 durable 和 DirectExchange 的 durable 不一致导致创建队列失败
     * 设置DirectExchange的durable为true解决
     * 猜测，设置队列的durable也能解决，但我没有试 嘿嘿
     * <p>
     * 自动删除
     *
     * @return DirectExchange
     */
    @Bean
    public Exchange directExchange() {
        DirectExchange exchange = new DirectExchange("exDirect", true, true);
        exchange.setAdminsThatShouldDeclare(amqpAdmin());
        return exchange;
    }

    @Bean
    public Binding bindingDirect(@Qualifier("directQueue") Queue directQueue, Exchange directExchange) {
        // with(routingKey) 这里把routingKey设置成queueName了
        Binding binding = BindingBuilder.bind(directQueue).to(directExchange).with(queueName).noargs();
        binding.setAdminsThatShouldDeclare(amqpAdmin());
        return binding;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        //若使用confirm-callback或return-callback，必须要配置publisherConfirms或publisherReturns为true
        //每个rabbitTemplate只能有一个confirm-callback和return-callback，如果这里配置了，那么写生产者的时候不能再写confirm-callback和return-callback
        //使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true
        //connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        //connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(myConnectionFactory);
        rabbitTemplate.setMandatory(true);
        /*
          如果消息没有到exchange,则confirm回调,ack=false
          如果消息到达exchange,则confirm回调,ack=true
          exchange到queue成功,则不回调return* exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
//                log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, true, cause);
            } else {
                log.info("消息发送失败:correlationData({}),ack({}),cause({})", correlationData, false, cause);
            }
        });
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) ->
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message));
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory manualSimpleRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(myConnectionFactory);
        //配置与yaml里配置的spring.rabbitmq.simple 下的一样
        factory.setConcurrentConsumers(10);
        factory.setMaxConcurrentConsumers(10);
        factory.setMissingQueuesFatal(false);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //未ack的消息个数超过此个数，则不再向这个消费者推送消息
        factory.setPrefetchCount(2);
//        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
//        converter.setDefaultCharset("UTF-8");
//        factory.setMessageConverter(converter);
        return factory;
    }

}
