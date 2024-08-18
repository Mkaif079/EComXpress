package com.example.product.controllers;

import com.example.product.exceptions.InvalidProductIdException;
import com.example.product.models.Product;
import com.example.product.services.IProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private IProductService productService;
    @Test
    void getAllProducts() {

        Product p1 = new Product();
        Product p2 = new Product();
        Product p3 = new Product();

        List<Product> mockedResponse = Arrays.asList(p1,p2,p3);

        Mockito.when(productService.getAllProducts()).thenReturn(mockedResponse);

        List<Product> products = productController.getAllProducts();
        Assertions.assertEquals(3 , products.size());
    }

    @Test
    void getAllProductTestForException() throws InvalidProductIdException {
        Mockito.when(
                productService.getSingleProduct(25L)
        ).thenThrow(InvalidProductIdException.class);
        Assertions.assertThrows(InvalidProductIdException.class ,
                ()->productController.getSingleProduct(25L)
        );
    }
}