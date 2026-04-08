package com.example.test.service;

import com.example.test.entity.Product;
import com.example.test.exception.ProductNotFoundException;
import com.example.test.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product uploadProduct(String name, MultipartFile image) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Product image is required");
        }

        try {
            Product product = Product.builder()
                    .name(name)
                    .image(image.getBytes())
                    .build();

            return productRepository.save(product);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to process uploaded image");
        }
    }

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }
}
