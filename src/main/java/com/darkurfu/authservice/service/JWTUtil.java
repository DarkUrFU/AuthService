package com.darkurfu.authservice.service;

import com.darkurfu.authservice.consts.PrivateConst;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.*;
import java.util.Date;


@Component()
public class JWTUtil {
    private final SecretKey secretKey;

    public JWTUtil(){
        this.secretKey = getSigningKey(
                PrivateConst.getSecretKey()
        );
    }

    private  SecretKey getSigningKey(String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public  String generateJWT(String role, String id){
        String jwt;

        LocalDate date = LocalDate.now(ZoneId.of("Asia/Yekaterinburg"));

        jwt = Jwts.builder()
                .header()
                .type("JWS")
                .add("alg", secretKey.getAlgorithm())

                .and()
                .claim("role", role) // moder or user or smth else

                .issuedAt(
                        Date.from(date.atStartOfDay(ZoneId.of("Asia/Yekaterinburg")).toInstant())) //date of generate token
                .expiration(
                        Date.from(date.plusDays(1).atStartOfDay(ZoneId.of("Asia/Yekaterinburg")).toInstant())) // date of end token

                .id(id)

                .signWith(secretKey) //set secret key
                .compact();



        return jwt;
    }


    public Jws<Claims> encryptJWT(String jwt) throws SignatureException {
        Jws<Claims> a = null;

        a = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt);

        //HashMap<String, String> v = (HashMap<String, String>) a.getPayload().get("user");

        return a;
    }
}

