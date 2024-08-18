package com.example.product.services;

import com.example.product.dtos.ProductRequestDto;
import com.example.product.exceptions.InvalidProductIdException;
import com.example.product.exceptions.ProductDoesNotExistException;
import com.example.product.models.Category;
import com.example.product.models.Product;
import com.example.product.repositories.CategoryRepository;
import com.example.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@Qualifier("selfProductService")
public class SelfProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public SelfProductService(ProductRepository productRepository , CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long id) throws InvalidProductIdException {
        Optional<Product> productOptional =  productRepository.findById(id);
        if(productOptional.isEmpty()){
            throw new InvalidProductIdException("Product with id "+id+" not found");
        }
        Product product = productOptional.get();
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(Product product) {
        Optional<Category> optionalCategory = categoryRepository.findByName(product.getCategory().getName());
        if(optionalCategory.isEmpty()){
//            Category categoryToSave = new Category();
//            categoryToSave.setName(product.getCategory().getName());
//            Category savedCategory = categoryRepository.save(categoryToSave);
//            product.setCategory(savedCategory);
        }else{
            product.setCategory(optionalCategory.get());
        }
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductDoesNotExistException {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){
            throw new ProductDoesNotExistException("Product with id "+id+" does not exist");
        }
        Product existingProduct = productOptional.get();
        Product updatedProduct = new Product();

        updatedProduct.setId(id);
        updatedProduct.setName(
                product.getName() != null ? product.getName() : existingProduct.getName()
        );
        updatedProduct.setDescription(
                product.getDescription() != null ? product.getDescription() : existingProduct.getDescription()
        );
        updatedProduct.setImage(
                product.getImage() != null ? product.getImage() : existingProduct.getImage()
        );
        updatedProduct.setPrice(
                product.getPrice() > 0 ? product.getPrice() : existingProduct.getPrice()
        );
        Optional<Category> optionalCategory = categoryRepository.findByName(product.getCategory().getName());
        if(optionalCategory.isEmpty()){
            Category categoryToSave = new Category();
            categoryToSave.setName(product.getCategory().getName());
            Category savedCategory = categoryRepository.save(categoryToSave);
            updatedProduct.setCategory(savedCategory);
        }else{
            updatedProduct.setCategory(optionalCategory.get());
        }
        Product savedUpdatedProduct = productRepository.save(updatedProduct);
        return savedUpdatedProduct;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> getProductsContainingAName(String name, int pageSize, int startingElementIndex) {
        return null;
    }
}
