package com.servpro.api.domain.port.in;

import com.servpro.api.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Input Port - Product Use Cases
 * Defines what the application can do
 */
public interface ProductUseCase {
    
    Product createProduct(Product product);
    
    Optional<Product> findById(Long id);
    
    List<Product> findAll();
    
    Product updateProduct(Long id, Product product);
    
    void deleteProduct(Long id);
    
    Product updateStock(Long id, Integer quantity);
}
