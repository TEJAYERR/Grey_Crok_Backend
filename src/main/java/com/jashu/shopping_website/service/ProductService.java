package com.jashu.shopping_website.service;

import com.jashu.shopping_website.dto.ProductUpdateRequest;
import com.jashu.shopping_website.entities.Product;
import com.jashu.shopping_website.dto.ProductResponse;
import com.jashu.shopping_website.entities.SubCategory;
import com.jashu.shopping_website.entities.User;
import com.jashu.shopping_website.repository.ProductRepo;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    ProductRepo productRepo;

    @Autowired
    ProductService(ProductRepo productRepo){

        this.productRepo = productRepo;
    }

    public List<ProductResponse> getProducts(){
        try {
            return productRepo.getProducts();
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong!!");
        }
    }

    public ProductResponse getProductResponseById(int id){

//        for(int i = 0; i < products.size(); i++){
//            if(products.get(i).getProductId() == id){
//                return products.get(i).toString();
//            }
//        }

//        return null;

//        return products.stream()
//                .filter(p -> p.getProductId() == id)
//                .findFirst().get().toString();

        return productRepo.getProductByProductId(id);
    }

    public Product getProductById(int id){
        return productRepo.findById(id).orElse(new Product());
    }

    public ProductResponse addProduct(Product newProduct, MultipartFile file){
        try {
            newProduct.setImageName(file.getOriginalFilename());
            newProduct.setImageType(file.getContentType());
            newProduct.setImageData(file.getBytes());

            Product product = productRepo.save(newProduct);
            return ProductResponse.getProductResponse(product);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ProductResponse updateProduct(int id, ProductUpdateRequest productUpdateRequest, MultipartFile imageFile) throws IOException {

//        for(int i = 0; i < products.size(); i++){
//            if(products.get(i).getProductId() == product.getProductId()){
//                products.set(i, product);
//                break;
//            }
//        }

        Product product = productRepo.findById(id).orElse(null);

        if(product == null){
            throw new IllegalArgumentException("Product does not exist");
        }

        product.setProductName(productUpdateRequest.getProductName());
        product.setProductDescription(productUpdateRequest.getProductDescription());
        product.setProductPrice(productUpdateRequest.getProductPrice());
        product.setProductRating(productUpdateRequest.getProductRating());
        product.setAvailable(productUpdateRequest.isAvailable());
        product.setQuantity(productUpdateRequest.getQuantity());
        product.setImageName(imageFile.getName());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        return ProductResponse.getProductResponse(productRepo.save(product));
    }

    public String deleteProduct(int id){

//        for(int i = 0; i < products.size(); i++){
//            if(products.get(i).getProductId() == id){
//                products.remove(i);
//                break;
//            }
//        }

        Product product = productRepo.findById(id).orElse(null);

        if(product == null){
            throw new IllegalArgumentException("Product does not exist");
        }

        productRepo.deleteById(id);
        return "deleted Successfully";
    }


    public List<ProductResponse> searchProductByKeyword(String keyword){

        List<ProductResponse> products = new ArrayList<>();
        List<ProductResponse> allProducts = productRepo.getProducts();

        for(ProductResponse productResponse : allProducts){
            if(productResponse.getProductName().contains(keyword)
                    || productResponse.getProductDescription().contains(keyword)){

                products.add(productResponse);
            }
        }


        if(products.isEmpty()){
            throw new RuntimeException("search not found !");
        }

        return products;
    }
}
