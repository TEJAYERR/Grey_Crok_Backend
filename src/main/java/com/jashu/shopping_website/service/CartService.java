package com.jashu.shopping_website.service;

import com.jashu.shopping_website.dto.AddToCartRequest;
import com.jashu.shopping_website.dto.CartItemResponse;
import com.jashu.shopping_website.dto.CartResponse;
import com.jashu.shopping_website.entities.Cart;
import com.jashu.shopping_website.entities.CartItem;
import com.jashu.shopping_website.entities.Product;
import com.jashu.shopping_website.entities.User;
import com.jashu.shopping_website.exception.ResourceNotFoundException;
import com.jashu.shopping_website.repository.CartItemRepo;
import com.jashu.shopping_website.repository.CartRepo;
import com.jashu.shopping_website.repository.ProductRepo;
import com.jashu.shopping_website.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartService {

    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final CartItemRepo cartItemRepo;
    private final CartRepo cartRepo;

    public CartService(UserRepo userRepo, ProductRepo productRepo, CartItemRepo cartItemRepo, CartRepo cartRepo){
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.cartItemRepo = cartItemRepo;
        this.cartRepo = cartRepo;
    }

    @Transactional
    public CartResponse getCartItems(UUID userId){

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found !"));

        Cart cart = user.getCart();

        if(cart == null || cart.getCartItems().isEmpty()){
            throw new ResourceNotFoundException("cart is empty !");
        }

        return new CartResponse(cart);
    }

    @Transactional
    public CartResponse addProductToCart(UUID userId, AddToCartRequest addToCartRequest){

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found !"));

        Product product = productRepo.findById(addToCartRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("product not found !"));

        Cart cart = user.getCart();
        if(cart == null){

            cart = new Cart();
            cart.setUser(user);
            cart.setCartItems(new ArrayList<>());
            user.setCart(cart);
        }

        List<CartItem> cartItems = cart.getCartItems();

        //If product already exists in the cart
        for (CartItem cartItem : cartItems) {

            if (cartItem.getProduct().getProductId().equals(addToCartRequest.getProductId())) {

                cartItem.setQuantity(cartItem.getQuantity() + addToCartRequest.getQuantity());
                return new CartResponse(cart);
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cart.getCartItems().add(cartItem);
        cartItem.setQuantity(addToCartRequest.getQuantity());

        //new cart Item
        cartItemRepo.save(cartItem);
        cartRepo.save(cart);
        userRepo.save(user);

        return new CartResponse(cart);
    }

    @Transactional
    public String deleteProductFromCart(UUID userId, UUID cartItemId){

        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("cart item not found !"));

        if(!cartItem.getCart().getUser().getUserId().equals(userId)){
            throw new ResourceNotFoundException("unauthorized");
        }

        cartItemRepo.deleteById(cartItemId);

        return "Product deleted Successfully";
    }
}
