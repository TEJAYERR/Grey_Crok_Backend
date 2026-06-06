package com.jashu.shopping_website.repository;

import com.jashu.shopping_website.entities.Category;
import com.jashu.shopping_website.entities.Product;
import com.jashu.shopping_website.dto.ProductResponse;
import com.jashu.shopping_website.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.xml.catalog.Catalog;
import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
//    List<Product> getProductsByProductId(int productId);
    @Query("""
        SELECT new com.jashu.shopping_website.dto.ProductResponse(
            p.productId,
            p.productName,
            p.productPrice,
            p.productDescription,
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
            p.quantity,
            p.available,
            p.productRating
        )
        FROM Product p 
        WHERE p.productId = :productId
        """)
    ProductResponse getProductByProductId(Integer productId);

    Product getByProductId(Integer productId);

    @Query("""

        SELECT new com.jashu.shopping_website.dto.ProductResponse(
            p.productId,
            p.productName,
            p.productPrice,
            p.productDescription,
            p.quantity,
            p.available,
            p.productRating
        ) from Product p
          where p.subCategory = :subCategory
    """)

    List<ProductResponse> getProductsBySubCategory(SubCategory subCategory);

    @Query("""

        SELECT new com.jashu.shopping_website.dto.ProductResponse(
            p.productId,
            p.productName,
            p.productPrice,
            p.productDescription,
            p.quantity,
            p.available,
            p.productRating
        ) from Product p
        where p.productName = :productName
    """)

    List<ProductResponse> getProductsByProductName(String productName);


    @Query("""

        SELECT new com.jashu.shopping_website.dto.ProductResponse(
            p.productId,
            p.productName,
            p.productPrice,
            p.productDescription,
            p.quantity,
            p.available,
            p.productRating
        ) from Product p
        where p.category = :category
    """)
    List<ProductResponse> getProductsByCategory(Category category);

}
