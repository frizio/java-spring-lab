package com.example.java_spring_lab;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class JavaSpringLabApplicationTests {

    @Autowired
    private RestTestClient restTestClient;

    @Test
    void shouldRunOnJava25() {
        String javaVersion = System.getProperty("java.version");
        System.out.println("Java version in uso: " + javaVersion);
        assertTrue(
                Runtime.version().feature() == 25,
                "L'applicazione deve essere eseguita con Java 25, versione rilevata: "
                        + javaVersion);
    }

    @Test
    public void healthEndpointShouldReturnUp() {

        restTestClient.get()
                .uri("/actuator/health")
                .exchange()
                .expectStatus()
                .isOk()
                /*
                 * .expectHeader()
                 * .contentTypeCompatibleWith(APPLICATION_JSON)
                 */
                .expectBody()
                .jsonPath("$.status")
                .isEqualTo("UP");
    }
}
