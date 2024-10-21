package com.abutua.productbackend.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.abutua.productbackend.models.Category;

public class CategoryRequest {

@Column(nullable = false, length = 255)
    @NotBlank(message = "Name can not be null")
    @Size(min=3, max = 255, message = "Name length min=3 and max=255")
    private String name;

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public Category toEntity(){
    return new Category(name);
}

    
    
}
