package com.example.product.services;

import com.example.product.dtos.ProductRequestDto;
import com.example.product.exceptions.InvalidProductIdException;
import com.example.product.exceptions.ProductDoesNotExistException;
import com.example.product.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {

    public Product getSingleProduct(Long id) throws InvalidProductIdException;

    List<Product> getAllProducts();

    Product addProduct(Product product);

    Product updateProduct(Long id, Product product) throws ProductDoesNotExistException;

    void delete(Long id);

    Page<Product> getProductsContainingAName(String name, int pageSize, int startingElementIndex);
}
