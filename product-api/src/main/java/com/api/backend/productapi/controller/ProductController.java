package com.api.backend.productapi.controller;

import com.api.backend.productapi.dto.ProductDTO;
import com.api.backend.productapi.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping
    public List<ProductDTO> getProducts(){
        return productService.getAll();
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductDTO> getProductByCategory(
            @PathVariable Long categoryId){
        return productService.getProductByCategoryId(categoryId);
    }

    @GetMapping("/{productIdentifier}")
    public ProductDTO findById(
            @PathVariable String productIdentifier){
        return productService.findByProductIdentifier(productIdentifier);
    }

    @PostMapping
    public ProductDTO newProduct(
            @Valid @RequestBody ProductDTO userDTO){
        return productService.save(userDTO);
    }

    @DeleteMapping("/{id}")
    public ProductDTO delete(@PathVariable Long id){
        return productService.delete(id);
    }

    @PostMapping("/{id}")
    public ProductDTO editProduct(@PathVariable Long id,
                                  @RequestBody ProductDTO productDTO){
        return productService.editProduct(id, productDTO);
    }

    @GetMapping("/pageable")
    public Page<ProductDTO> getProductsPage(Pageable pageable){
        return productService.getAllPage(pageable);
    }
}
