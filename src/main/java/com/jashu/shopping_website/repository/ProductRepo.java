package com.jashu.shopping_website.repository;

import com.jashu.shopping_website.entities.Category;
import com.jashu.shopping_website.entities.Product;
import com.jashu.shopping_website.dto.ProductResponse;
import com.jashu.shopping_website.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepo extends JpaRepository<Product, UUID> {
    @Query("""
        SELECT new com.jashu.shopping_website.dto.ProductResponse(
            p.productId,
            p.productName,
            p.productPrice,
            p.productDescription,
            p.quantity,
            p.available,
            p.productRating,
            p.category,
            p.subCategory
        )
        FROM Product p
        """)
    List<ProductResponse> getProducts();

    @Query("""
        SELECT new com.jashu.shopping_website.dto.ProductResponse(
            p.productId,
            p.productName,
            p.productPrice,
            p.productDescription,
            p.quantity,
            p.available,
            p.productRating,
            p.category,
            p.subCategory
        )
        FROM Product p 
        WHERE p.productId = :productId
        """)
    ProductResponse getProductByProductId(UUID productId);

}
