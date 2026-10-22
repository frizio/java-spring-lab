package dev.frizio.lab.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.frizio.lab.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
