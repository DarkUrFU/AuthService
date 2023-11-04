package com.darkurfu.authservice.jwt;


import com.darkurfu.authservice.service.cryptutils.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class JWTUtilTest {

    private String badJwt = "eyJ0eXAiOiJKV1MiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiYWRtaW4iLCJpYXQiOjE2OTg5NTE2MDAsImV4cCI6MTY5OTAzODAwMCwianRpIjoiMSJ9.2p9jq8yhyNeNcXQTqOx3YaM67J6vZ1KoQUN9X_KQmWg";
    private String badSecret = "BadSecretKeyForTest0123456789AddWords4Algorithm";

    static JWTUtil jwtUtil = new JWTUtil();


    static private String generatedJWT;


    @BeforeAll
    static void generateData(){
        generatedJWT = jwtUtil.generateAccessJWT("admin",1l,1l);

        System.out.println(generatedJWT);
    }

    @Test
    void generateJWT(){
        System.out.println(generatedJWT);
    }

    @Test
    void readJWT(){
        Jws<Claims> jws = jwtUtil.encryptJWT(generatedJWT);

        System.out.println(jws.toString());
    }

    @Test
    void readBadJWT(){
        Jws<Claims> jws;
        try {
            System.out.println(badJwt);
            jws = jwtUtil.encryptJWT(badJwt);

            System.out.println(jws.toString());
        } catch (SignatureException e){

            System.out.println(e.getMessage());
        }

    }
}
