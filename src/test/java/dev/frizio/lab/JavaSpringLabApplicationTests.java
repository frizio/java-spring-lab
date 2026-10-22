package dev.frizio.lab;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.core.SpringVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import dev.frizio.lab.model.Product;
import dev.frizio.lab.service.ProductService;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "management.health.mongodb.enabled=false")
@AutoConfigureRestTestClient
class JavaSpringLabApplicationTests {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private ProductService productService;

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

    @Test
    void runtimeInfoEndpointShouldReturnJvmAndSpringDetails() {
        restTestClient.get()
                .uri("/runtime-info")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.jvmVersion")
                .isEqualTo(System.getProperty("java.vm.version"))
                .jsonPath("$.jvmVendor")
                .isEqualTo(System.getProperty("java.vm.vendor"))
                .jsonPath("$.springVersion")
                .isEqualTo(SpringVersion.getVersion())
                .jsonPath("$.springBootVersion")
                .isEqualTo(SpringBootVersion.getVersion())
                .jsonPath("$.vendor")
                .isEqualTo(System.getProperty("java.vendor"));
    }

    @Test
    void productsEndpointShouldReturnProductList() {
        when(productService.getAllProducts())
                .thenReturn(List.of(new Product("507f1f77bcf86cd799439011", "Widget", 12.5, true)));

        restTestClient.get()
                .uri("/products")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$[0].id")
                .isEqualTo("507f1f77bcf86cd799439011")
                .jsonPath("$[0].name")
                .isEqualTo("Widget")
                .jsonPath("$[0].price")
                .isEqualTo(12.5)
                .jsonPath("$[0].promo")
                .isEqualTo(true);
    }

    @Test
    void productEndpointShouldReturnProductById() {
        when(productService.getProductById("507f1f77bcf86cd799439011"))
                .thenReturn(Optional.of(new Product("507f1f77bcf86cd799439011", "Widget", 12.5, true)));

        restTestClient.get()
                .uri("/products/507f1f77bcf86cd799439011")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id")
                .isEqualTo("507f1f77bcf86cd799439011")
                .jsonPath("$.name")
                .isEqualTo("Widget");
    }

    @Test
    void productEndpointShouldReturnNotFoundForUnknownId() {
        when(productService.getProductById("507f1f77bcf86cd799439012"))
                .thenReturn(Optional.empty());

        restTestClient.get()
                .uri("/products/507f1f77bcf86cd799439012")
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
