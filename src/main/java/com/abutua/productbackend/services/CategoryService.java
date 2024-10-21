package com.abutua.productbackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.abutua.productbackend.dto.CategoryRequest;
import com.abutua.productbackend.dto.CategoryResponse;
import com.abutua.productbackend.models.Category;
import com.abutua.productbackend.repositories.CategoryRepository;
import com.abutua.productbackend.services.exceptions.DataBaseException;

import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

   

    public CategoryResponse getById(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        return category.toDTO();
    }

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream().map(c -> c.toDTO()).collect(Collectors.toList());
    }

    public CategoryResponse save(CategoryRequest categoryRequest) {
        Category category = categoryRepository.save(categoryRequest.toEntity());
        return category.toDTO();
    }

    public void deleteById(int id) {
        try{
        categoryRepository.deleteById(id);
        } catch(DataIntegrityViolationException e){
            throw new DataBaseException("Constrain violation, category can't be deleted");

        } catch(EmptyResultDataAccessException e){
            throw new EntityNotFoundException("Category not found");
        }
    }

    public void update(int id, CategoryRequest categoryUpdate) {

        try {
            Category category = categoryRepository.getReferenceById(id);
            category.setName(categoryUpdate.getName());
        categoryRepository.save(category);    
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Category not found");
        }
        
        
    }

}
