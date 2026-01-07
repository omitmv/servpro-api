package com.servpro.api.infrastructure.adapter.out.persistence.mapper;

import com.servpro.api.domain.model.Product;
import com.servpro.api.infrastructure.adapter.out.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper between Domain entities and JPA entities
 */
@Component
public class ProductPersistenceMapper {
    
    public ProductEntity toEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
    
    public Product toDomain(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
