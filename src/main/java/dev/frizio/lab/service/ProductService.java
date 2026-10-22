package dev.frizio.lab.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import dev.frizio.lab.model.Product;
import dev.frizio.lab.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        if (!ObjectId.isValid(id)) {
            return Optional.empty();
        }
        return productRepository.findById(id);
    }
}
