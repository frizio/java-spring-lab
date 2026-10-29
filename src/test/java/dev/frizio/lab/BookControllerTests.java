package dev.frizio.lab;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import dev.frizio.lab.dto.BookDto;
import dev.frizio.lab.dto.BookRequestDto;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "management.health.mongodb.enabled=false")
@AutoConfigureRestTestClient
class BookControllerTests {

    @Autowired
    private RestTestClient restTestClient;

    @Test
    void shouldCreateReadUpdateAndDeleteBook() {
        BookDto createdBook = restTestClient.post()
                .uri("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new BookRequestDto("The Hobbit", "J. R. R. Tolkien", 1937))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectHeader()
                .exists("Location")
                .expectBody(BookDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(createdBook);
        assertNotNull(createdBook.id());

        restTestClient.get()
                .uri("/books")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$[0].id")
                .isEqualTo(createdBook.id())
                .jsonPath("$[0].title")
                .isEqualTo("The Hobbit")
                .jsonPath("$[0].author")
                .isEqualTo("J. R. R. Tolkien")
                .jsonPath("$[0].year")
                .isEqualTo(1937);

        restTestClient.get()
                .uri("/books/{id}", createdBook.id())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.title")
                .isEqualTo("The Hobbit");

        restTestClient.put()
                .uri("/books/{id}", createdBook.id())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new BookRequestDto("The Lord of the Rings", "J. R. R. Tolkien", 1954))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.title")
                .isEqualTo("The Lord of the Rings")
                .jsonPath("$.year")
                .isEqualTo(1954);

        restTestClient.delete()
                .uri("/books/{id}", createdBook.id())
                .exchange()
                .expectStatus()
                .isNoContent();

        restTestClient.get()
                .uri("/books/{id}", createdBook.id())
                .exchange()
                .expectStatus()
                .isNotFound();

        restTestClient.delete()
                .uri("/books/{id}", createdBook.id())
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void shouldRejectInvalidBookData() {
        restTestClient.post()
                .uri("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new BookRequestDto("", "Author", 0))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
}
