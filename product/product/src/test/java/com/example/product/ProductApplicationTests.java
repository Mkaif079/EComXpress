package com.example.product;

import com.example.product.models.Category;
import com.example.product.models.Product;
import com.example.product.repositories.CategoryRepository;
import com.example.product.repositories.ProductWithPriceAndDescription;
import com.example.product.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductApplicationTests {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	CategoryRepository categoryRepository;
//	@Test
//	void contextLoads() {
//	}
//	@Test
//	void getSomeData(){
//		Product p = productRepository.something(1L);
//		System.out.println(p.getName() +" "+p.getDescription());
//	}
	@Test
	void getSomeMoreData(){
		ProductWithPriceAndDescription p = productRepository.somethingMore(2L);
		System.out.println(p.getId() +" "+p.getDescription()+" "+p.getPrice());
	}

	@Test
	void getWithNative(){
		Product p = productRepository.somethingNative(1L);
		System.out.println(p.getName());
	}
	@Test
	void testFetchType(){
		categoryRepository.findById(1L);
	}
}
