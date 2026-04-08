package com.jashu.shopping_website.service;

import com.jashu.shopping_website.dto.AddToCartRequest;
import com.jashu.shopping_website.dto.CartItemResponse;
import com.jashu.shopping_website.entities.Cart;
import com.jashu.shopping_website.entities.CartItem;
import com.jashu.shopping_website.entities.Product;
import com.jashu.shopping_website.entities.User;
import com.jashu.shopping_website.repository.CartRepo;
import com.jashu.shopping_website.repository.ProductRepo;
import com.jashu.shopping_website.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    UserRepo userRepo;
    CartRepo cartRepo;
    ProductRepo productRepo;

    @Autowired
    public void setUserRepo(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Autowired
    public void setProductRepo(ProductRepo productRepo){
        this.productRepo = productRepo;
    }

    @Autowired
    public void setCartRepo(CartRepo cartRepo){
        this.cartRepo = cartRepo;
    }

    public List<CartItemResponse> getCartItems(String email){

        User user = userRepo.getByEmail(email);

        if(user == null){
            throw new IllegalArgumentException("No User Exists");
        }

        System.out.println("Hello Exiting");

        List<CartItem> cartItems = user.getCart().getCartItems();

        List<CartItemResponse> cartItemResponses = new ArrayList<>();

        for(CartItem cartItem : cartItems){
            CartItemResponse cartItemResponse = new CartItemResponse();
            cartItemResponse.setProductId(cartItem.getProduct().getProductId());
            cartItemResponse.setQuantity(cartItem.getQuantity());

            cartItemResponses.add(cartItemResponse);
        }

        return cartItemResponses;
    }

    public String addProductToCart(AddToCartRequest addToCartRequest, String email){

        User user = userRepo.getByEmail(email);

        if(user == null){
            throw new IllegalArgumentException("No User Exists");
        }

        Product product = productRepo.getByProductId(addToCartRequest.getProductId());

        if(product == null){
            throw new IllegalArgumentException("No product Exist");
        }

        if(user.getCart() == null){

            Cart cart = new Cart();

            cart.setUser(user);

            cart.setCartItems(new ArrayList<>());

            user.setCart(cart);
        }

        List<CartItem> cartItems = user.getCart().getCartItems();

        for (CartItem cartItem : cartItems){
            if(cartItem.getProduct().getProductId() == addToCartRequest.getProductId()){
                cartItem.setQuantity(cartItem.getQuantity() + addToCartRequest.getQuantity());
                userRepo.save(user);
                return "Product added Successfully";
            }
        }

        CartItem cartItem = new CartItem();

        cartItem.setCart(user.getCart());

        cartItem.setProduct(product);

        user.getCart().getCartItems().add(cartItem);

        cartItem.setQuantity(addToCartRequest.getQuantity());

        userRepo.save(user);

        return "Product added to cart successfully !!";
    }

    public String deleteProductFromCart(int id, String email){

        User user = userRepo.getByEmail(email);

        if(user == null){
            throw new IllegalArgumentException("No User Exists");
        }

        if(user.getCart() == null){
            throw new IllegalArgumentException("No products Exist in cart to delete");
        }

        List<CartItem> cartItems = user.getCart().getCartItems();

        for (CartItem cartItem : cartItems){

            if(cartItem.getProduct().getProductId() == id){

                cartItems.remove(cartItem);

                user.getCart().setCartItems(cartItems);

                userRepo.save(user);

                return "Product deleted Successfully";
            }
        }

        return "Product does not exist in cart";
    }


}
