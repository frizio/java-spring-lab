package dev.frizio.lab;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import dev.frizio.lab.dto.ProductDto;
import dev.frizio.lab.dto.ProductRequestDto;
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
                .thenReturn(List.of(new ProductDto("507f1f77bcf86cd799439011", "Widget", 12.5, true)));

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
                .thenReturn(Optional.of(new ProductDto("507f1f77bcf86cd799439011", "Widget", 12.5, true)));

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

    @Test
    void productEndpointShouldCreateProduct() {
        ProductRequestDto request = new ProductRequestDto("Widget", 12.5, true);
        when(productService.createProduct(request))
                .thenReturn(new ProductDto("507f1f77bcf86cd799439011", "Widget", 12.5, true));

        restTestClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectHeader()
                .exists("Location")
                .expectBody()
                .jsonPath("$.id")
                .isEqualTo("507f1f77bcf86cd799439011")
                .jsonPath("$.name")
                .isEqualTo("Widget");
    }

    @Test
    void productEndpointShouldUpdateProduct() {
        ProductRequestDto request = new ProductRequestDto("Updated widget", 15.0, false);
        when(productService.updateProduct("507f1f77bcf86cd799439011", request))
                .thenReturn(Optional.of(
                        new ProductDto("507f1f77bcf86cd799439011", "Updated widget", 15.0, false)));

        restTestClient.put()
                .uri("/products/507f1f77bcf86cd799439011")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.name")
                .isEqualTo("Updated widget")
                .jsonPath("$.price")
                .isEqualTo(15.0)
                .jsonPath("$.promo")
                .isEqualTo(false);
    }

    @Test
    void productEndpointShouldReturnNotFoundWhenUpdatingUnknownProduct() {
        when(productService.updateProduct(
                "507f1f77bcf86cd799439012",
                new ProductRequestDto("Widget", 12.5, true)))
                .thenReturn(Optional.empty());

        restTestClient.put()
                .uri("/products/507f1f77bcf86cd799439012")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ProductRequestDto("Widget", 12.5, true))
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void productEndpointShouldDeleteProduct() {
        when(productService.deleteProduct("507f1f77bcf86cd799439011")).thenReturn(true);

        restTestClient.delete()
                .uri("/products/507f1f77bcf86cd799439011")
                .exchange()
                .expectStatus()
                .isNoContent();

        verify(productService).deleteProduct("507f1f77bcf86cd799439011");
    }

    @Test
    void productEndpointShouldReturnNotFoundWhenDeletingUnknownProduct() {
        when(productService.deleteProduct("507f1f77bcf86cd799439012")).thenReturn(false);

        restTestClient.delete()
                .uri("/products/507f1f77bcf86cd799439012")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void productEndpointShouldRejectInvalidProductData() {
        restTestClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ProductRequestDto("", -1.0, null))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
}
