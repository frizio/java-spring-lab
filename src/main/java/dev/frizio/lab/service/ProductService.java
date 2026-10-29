package dev.frizio.lab.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import dev.frizio.lab.dto.ProductDto;
import dev.frizio.lab.model.Product;
import dev.frizio.lab.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public Optional<ProductDto> getProductById(String id) {
        if (!ObjectId.isValid(id)) {
            return Optional.empty();
        }
        return productRepository.findById(id).map(this::toDto);
    }

    private ProductDto toDto(Product product) {
        return new ProductDto(product.id(), product.name(), product.price(), product.promo());
    }
}
