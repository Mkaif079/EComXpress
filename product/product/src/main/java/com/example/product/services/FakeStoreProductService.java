package com.example.product.services;

import com.example.product.dtos.ProductRequestDto;
import com.example.product.dtos.ProductResponseDto;
import com.example.product.exceptions.InvalidProductIdException;
import com.example.product.models.Category;
import com.example.product.models.Product;
import com.example.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("fakeStoreProductService")
public class FakeStoreProductService implements IProductService{
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RedisTemplate<String , Long > redisTemplate;

    public Product getProductFromResponseDto(ProductResponseDto responseDto){
        Product product = new Product();
        product.setId(responseDto.getId());
        product.setName(responseDto.getTitle());
        product.setPrice(responseDto.getPrice());
        product.setDescription(responseDto.getDescription());
        product.setImage(responseDto.getImage());
        product.setCategory(new Category());
        product.getCategory().setName(responseDto.getCategory());
        return product;
    }
    @Override
    public Product getSingleProduct(Long id) throws InvalidProductIdException {

        if(redisTemplate.opsForHash().hasKey("PRODUCTS" , id)){
            return (Product) redisTemplate.opsForHash().get("PRODUCTS",id);
        }

        if(id>20){
            throw new InvalidProductIdException("");
        }
        ProductResponseDto response =  restTemplate.getForObject("https://fakestoreapi.com/products/"+id , ProductResponseDto.class);

        Product product = getProductFromResponseDto(response);
        redisTemplate.opsForHash().put("PRODUCTS",id , product);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        ProductResponseDto[] responseDtoList =  restTemplate.getForObject("https://fakestoreapi.com/products/" , ProductResponseDto[].class);
        List<Product> output = new ArrayList<>();
        for(ProductResponseDto productResponseDto : responseDtoList){
            output.add(getProductFromResponseDto(productResponseDto));
        }
        return output;
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Page<Product> getProductsContainingAName(String name, int pageSize, int startingElementIndex) {
        Page<Product> productPage = productRepository.findAllByNameContaining(
                name, PageRequest.of(startingElementIndex/pageSize , pageSize));
        return productPage;
    }

//    @Override
//    public Product updateProduct(Long id, ProductRequestDto productRequestDto) {
//        RequestCallback requestCallback = restTemplate.httpEntityCallback(ProductRequestDto.class , ProductResponseDto.class);
//        HttpMessageConverterExtractor<ProductResponseDto> responseExtractor = new HttpMessageConverterExtractor<>(ProductResponseDto.class ,restTemplate.getMessageConverters());
//
//        ProductResponseDto productResponseDto = restTemplate.execute("https://fakestoreapi.com/products/"+id , HttpMethod.PUT , requestCallback , responseExtractor);
//        return getProductFromResponseDto(productResponseDto);
//    }
}
