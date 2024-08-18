package com.example.product.controllers;

import com.example.product.dtos.ProductRequestDto;
import com.example.product.dtos.ProductWrapper;
import com.example.product.exceptions.ProductDoesNotExistException;
import com.example.product.models.Category;
import com.example.product.models.Product;
import com.example.product.services.IProductService;
import com.example.product.exceptions.InvalidProductIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private IProductService productService;

    @Autowired
    public ProductController(@Qualifier("selfProductService") IProductService productService){
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/products/search")
    public Page<Product> getProductsByName(@RequestParam("name") String name,
                                           @RequestParam("pageSize") int pageSize,
                                           @RequestParam("startingElementIndex") int startingElementIndex){
        return productService.getProductsContainingAName(name , pageSize , startingElementIndex);
    }

    @GetMapping("products/{id}")
    public ResponseEntity<ProductWrapper> getSingleProduct(@PathVariable("id") Long id) throws InvalidProductIdException {
        ResponseEntity<ProductWrapper> responseEntity;
        Product singleProduct = productService.getSingleProduct(id);
        ProductWrapper productWrapper = new ProductWrapper(singleProduct ,"Successfully retrieved the data");
        responseEntity = new ResponseEntity<>(productWrapper , HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("products")
    public Product addProduct(@RequestBody ProductRequestDto productRequestDto){
        Product product = new Product();
        product.setName(productRequestDto.getTitle());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setImage(product.getImage());
        product.setCategory(new Category());
        product.getCategory().setName(productRequestDto.getCategory());
        Product savedProduct = productService.addProduct(product);
        return savedProduct;
    }

    @PutMapping("products/{id}")
    public Product updateProduct(@PathVariable("id") Long id , @RequestBody ProductRequestDto productRequestDto) throws ProductDoesNotExistException {
        Product product = new Product();
        product.setId(id);
        product.setName(productRequestDto.getTitle());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setImage(product.getImage());
        product.setCategory(new Category());
        product.getCategory().setName(productRequestDto.getCategory());
        return productService.updateProduct(id , product);
    }

    @DeleteMapping("products/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        productService.delete(id);
    }
}
