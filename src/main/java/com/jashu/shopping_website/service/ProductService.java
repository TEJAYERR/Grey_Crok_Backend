package com.jashu.shopping_website.service;

import com.jashu.shopping_website.dto.ProductUpdateRequest;
import com.jashu.shopping_website.entities.Product;
import com.jashu.shopping_website.dto.ProductResponse;
import com.jashu.shopping_website.entities.User;
import com.jashu.shopping_website.exception.ResourceNotFoundException;
import com.jashu.shopping_website.repository.ProductRepo;
import com.jashu.shopping_website.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final UserRepo userRepo;
    ProductRepo productRepo;

    ProductService(ProductRepo productRepo, UserRepo userRepo){
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    public List<ProductResponse> getProducts(){
        return productRepo.getProducts();
    }

    public ProductResponse getProductResponseById(UUID productId){

        ProductResponse productResponse = productRepo.getProductByProductId(productId);

        if (productResponse == null){
            throw new ResourceNotFoundException("product not found !");
        }

        return productResponse;
    }

    public Product getProductById(UUID productId){

        return productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product not found"));
    }

    public ProductResponse addProduct(UUID userId, Product newProduct, MultipartFile file) throws IOException {

        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        newProduct.setImageName(file.getOriginalFilename());
        newProduct.setImageType(file.getContentType());
        newProduct.setImageData(file.getBytes());

        newProduct.setAvailable(true);
        System.out.println(newProduct);

        Product product = productRepo.save(newProduct);

        return new ProductResponse(product);
    }

    public ProductResponse updateProduct(UUID userId,
                                         UUID productId,
                                         Product productUpdateRequest,
                                         MultipartFile imageFile) throws IOException {

        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if(productUpdateRequest.getProductName() != null)
            product.setProductName(productUpdateRequest.getProductName());

        if(productUpdateRequest.getProductDescription() != null)
            product.setProductDescription(productUpdateRequest.getProductDescription());

        if(productUpdateRequest.getProductPrice() != null)
            product.setProductPrice(productUpdateRequest.getProductPrice());

        if(productUpdateRequest.getProductRating() != 0)
            product.setProductRating(productUpdateRequest.getProductRating());

        product.setAvailable(productUpdateRequest.getAvailable());
        product.setQuantity(productUpdateRequest.getQuantity());

        if(productUpdateRequest.getImageName() != null)
            product.setImageName(imageFile.getName());

        if(productUpdateRequest.getImageType() != null)
            product.setImageType(imageFile.getContentType());

        if(productUpdateRequest.getImageData() != null)
            product.setImageData(imageFile.getBytes());

        if(productUpdateRequest.getQuantity() >= 1){
            product.setAvailable(true);
        }

        return new ProductResponse(productRepo.save(product));
    }

    public String deleteProduct(UUID userId, UUID productId){

        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Product product = productRepo.findById(productId).orElse(null);

        if(product == null){
            throw new ResourceNotFoundException("Product does not exist");
        }

        productRepo.deleteById(productId);

        return "deleted Successfully";
    }
}
