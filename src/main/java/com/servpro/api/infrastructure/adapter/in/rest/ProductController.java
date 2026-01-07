package com.servpro.api.infrastructure.adapter.in.rest;

import com.servpro.api.domain.model.Product;
import com.servpro.api.domain.port.in.ProductUseCase;
import com.servpro.api.infrastructure.adapter.in.rest.dto.ProductRequest;
import com.servpro.api.infrastructure.adapter.in.rest.dto.ProductResponse;
import com.servpro.api.infrastructure.adapter.in.rest.mapper.ProductRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller - Input Adapter
 * Exposes HTTP endpoints
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "API para gerenciamento de produtos")
public class ProductController {
    
    private final ProductUseCase productUseCase;
    private final ProductRestMapper mapper;
    
    @Operation(
            summary = "Criar novo produto",
            description = "Cria um novo produto no sistema com as informações fornecidas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Produto criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos"
            )
    })
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do produto a ser criado",
                    required = true
            )
            @Valid @RequestBody ProductRequest request) {
        Product product = mapper.toDomain(request);
        Product createdProduct = productUseCase.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(createdProduct));
    }
    
    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna os detalhes de um produto específico pelo seu identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto encontrado",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(
            @Parameter(description = "ID do produto", required = true)
            @PathVariable Long id) {
        return productUseCase.findById(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(
            summary = "Listar todos os produtos",
            description = "Retorna uma lista com todos os produtos cadastrados no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de produtos retornada com sucesso"
            )
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productUseCase.findAll();
        return ResponseEntity.ok(mapper.toResponseList(products));
    }
    
    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza as informações de um produto existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "ID do produto", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do produto",
                    required = true
            )
            @Valid @RequestBody ProductRequest request) {
        Product product = mapper.toDomain(request);
        Product updatedProduct = productUseCase.updateProduct(id, product);
        return ResponseEntity.ok(mapper.toResponse(updatedProduct));
    }
    
    @Operation(
            summary = "Deletar produto",
            description = "Remove um produto do sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Produto deletado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID do produto", required = true)
            @PathVariable Long id) {
        productUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(
            summary = "Atualizar estoque",
            description = "Atualiza a quantidade em estoque de um produto. Use valores positivos para adicionar e negativos para remover"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estoque atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Quantidade inválida ou estoque insuficiente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado"
            )
    })
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductResponse> updateStock(
            @Parameter(description = "ID do produto", required = true)
            @PathVariable Long id,
            @Parameter(
                    description = "Quantidade a adicionar (positivo) ou remover (negativo) do estoque",
                    required = true,
                    example = "10"
            )
            @RequestParam Integer quantity) {
        Product product = productUseCase.updateStock(id, quantity);
        return ResponseEntity.ok(mapper.toResponse(product));
    }
}
