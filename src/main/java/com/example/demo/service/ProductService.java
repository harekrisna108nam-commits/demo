package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductRequest;
import com.example.demo.dto.ProductResponse;
import com.example.demo.entity.Product;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        log.info("Fetching all products");
        List<ProductResponse> products = productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
        log.info("Successfully fetched {} products", products.size());
        return products;
    }

    public ProductResponse getProductById(Long id) {
        log.info("Fetching product with id: {}", id);
        ProductResponse response = toResponse(findProductOrThrow(id));
        log.info("Successfully fetched product with id: {}", id);
        return response;
    }

    public ProductResponse createProduct(ProductRequest request) {
        log.info("Creating product with name: {}", request.getName());
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
        ProductResponse response = toResponse(productRepository.save(product));
        log.info("Successfully created product with id: {}", response.getId());
        return response;
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        log.info("Updating product with id: {}", id);
        Product product = findProductOrThrow(id);
        log.debug("Found product to update: name={}, price={}, quantity={}", 
                product.getName(), product.getPrice(), product.getQuantity());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        ProductResponse response = toResponse(productRepository.save(product));
        log.info("Successfully updated product with id: {}", id);
        return response;
    }

    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        productRepository.delete(findProductOrThrow(id));
        log.info("Successfully deleted product with id: {}", id);
    }

    private Product findProductOrThrow(Long id) {
        log.debug("Looking up product with id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found with id: {}", id);
                    return new ResourceNotFoundException("Product not found with id: " + id);
                });
    }

    private ProductResponse toResponse(Product product) {
        log.debug("Mapping product to response for id: {}", product.getId());
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }
}