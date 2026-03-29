package com.jashu.shopping_website.repository;

import com.jashu.shopping_website.entities.Product;
import com.jashu.shopping_website.dto.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
//    List<Product> getProductsByProductId(int productId);
    @Query("""
SELECT new com.jashu.shopping_website.dto.ProductResponse(
    p.productId,
    p.productName,
    p.productPrice,
    p.productDescription,
    p.brand,
    p.quantity,
    p.available,
    p.productRating
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
    p.brand,
    p.quantity,
    p.available,
    p.productRating
)
FROM Product p 
WHERE p.productId = :productId
""")
    ProductResponse getProductByProductId(Integer productId);

    Product findProductByProductId(Integer productId);

    Product getByProductId(Integer productId);
}
