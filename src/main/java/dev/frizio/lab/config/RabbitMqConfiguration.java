package dev.frizio.lab.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    public static final String MESSAGE_QUEUE = "lab.messages";

    @Bean
    Queue messageQueue() {
        return new Queue(MESSAGE_QUEUE, true);
    }
}
