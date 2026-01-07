package com.servpro.api.infrastructure.adapter.out.persistence;

import com.servpro.api.infrastructure.adapter.out.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository
 */
@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
