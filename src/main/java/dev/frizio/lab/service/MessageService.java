package dev.frizio.lab.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import dev.frizio.lab.config.RabbitMqConfiguration;

@Service
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    private final RabbitTemplate rabbitTemplate;

    public MessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(String message) {
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.MESSAGE_QUEUE, message);
    }

    @RabbitListener(queues = RabbitMqConfiguration.MESSAGE_QUEUE)
    public void consume(String message) {
        log.info("Messaggio ricevuto da RabbitMQ: {}", message);
    }
}
