package com.example.product.repositories;

import com.example.product.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product , Long> {

    //Optional<Product> findByName(String name);
    Optional<Product> findById(Long id);

    Product save(Product product);

    void deleteById(Long id);

    Page<Product> findAllByNameContaining(String name , Pageable pageable);

    //List<Product> findByNameAndDescriptionAndPriceGreaterThan(String title , String description , int price);
    //void deleteById(Long id);
    @Query("select p from Product p where p.id = :kaif")
    Product something(@Param("kaif") Long id);

    @Query("select p.id as id , p.description as description , p.price as price from Product p where p.id = :id")
    ProductWithPriceAndDescription somethingMore(@Param("id") Long id);

    @Query(value = "select * from product where id = :native" , nativeQuery = true)
    Product somethingNative(@Param("native") Long id);
}
