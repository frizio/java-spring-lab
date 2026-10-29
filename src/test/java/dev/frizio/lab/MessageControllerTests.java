package dev.frizio.lab;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import dev.frizio.lab.service.MessageService;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "management.health.mongodb.enabled=false")
@AutoConfigureRestTestClient
class MessageControllerTests {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private MessageService messageService;

    @Test
    void messagesEndpointShouldPublishMessage() {
        restTestClient.post()
                .uri("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\":\"Ciao RabbitMQ!\"}")
                .exchange()
                .expectStatus()
                .isAccepted();

        verify(messageService).publish("Ciao RabbitMQ!");
    }

    @Test
    void messagesEndpointShouldRejectBlankMessage() {
        restTestClient.post()
                .uri("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\":\" \"}")
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
}
