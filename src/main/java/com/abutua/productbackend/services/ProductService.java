package com.abutua.productbackend.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.abutua.productbackend.dto.ProductRequest;
import com.abutua.productbackend.dto.ProductResponse;
import com.abutua.productbackend.models.Category;
import com.abutua.productbackend.models.Product;
import com.abutua.productbackend.repositories.ProductRepository;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse getById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return product.toDTO();
    }

    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream().map(p -> p.toDTO()).collect(Collectors.toList());
    }

    public ProductResponse save(ProductRequest productRequest) {
        try {
            Product newProduct = productRepository.save(productRequest.toEntity());
        return newProduct.toDTO();
        } catch (DataIntegrityViolationException e) {
            throw new EntityNotFoundException("Category not found");
        }
    }

    public void deleteById(long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Product not found");
        }
    }

    public void update(long id, ProductRequest productUpdate) {
        try {
            Product product = productRepository.getReferenceById(id);

            Category category = new Category(productUpdate.getCategory().getId());

            product.setDescription(productUpdate.getDescription());
            product.setName(productUpdate.getName());
            product.setPrice(productUpdate.getPrice());
            product.setNewProduct(productUpdate.isNewProduct());
            product.setPromotion(productUpdate.isPromotion());
            product.setCategory(category);

            productRepository.save(product);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Product not found");

        } catch (DataIntegrityViolationException e) {
            throw new EntityNotFoundException("Category not found");
        }
    }
}
