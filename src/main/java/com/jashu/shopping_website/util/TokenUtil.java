package com.jashu.shopping_website.util;

import java.util.Base64;

public class TokenUtil {

    public static String encodeToken(String email, String role){
        String token = email + ":" + role;
        byte[] bytes = token.getBytes();

        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String[] decodeToken(String encodedToken){
        byte[] bytes = Base64.getDecoder().decode(encodedToken);
        return new String(bytes).split(":");
    }

}
