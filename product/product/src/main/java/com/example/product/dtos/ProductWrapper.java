package com.example.product.dtos;

import com.example.product.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductWrapper {
    private Product product;
    private String message;

    public ProductWrapper(Product product, String message) {
        this.product = product;
        this.message = message;
    }
}
