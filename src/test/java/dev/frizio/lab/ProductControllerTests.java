package dev.frizio.lab;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import dev.frizio.lab.model.Product;
import dev.frizio.lab.service.ProductService;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "management.health.mongodb.enabled=false")
@AutoConfigureRestTestClient
class ProductControllerTests {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private ProductService productService;

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
