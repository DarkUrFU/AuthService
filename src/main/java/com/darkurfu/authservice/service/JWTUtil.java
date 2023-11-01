package com.darkurfu.authservice.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;


@Component()
public class JWTUtil {
    private final SecretKey secretKey;

    public JWTUtil(){
        this.secretKey = getSigningKey("secret key");
    }

    private  SecretKey getSigningKey(String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public  String generateJWT(){
        String jwt;

        jwt = Jwts.builder()

                .header()
                .and()
                .signWith(secretKey)
                .compact();



        return jwt;
    }


    public String encryptJWT(String jwt){
        Jws<Claims> a = null;

        a = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt);

        //HashMap<String, String> v = (HashMap<String, String>) a.getPayload().get("user");

        return a.toString();
    }
}

