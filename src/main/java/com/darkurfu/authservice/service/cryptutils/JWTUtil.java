package com.darkurfu.authservice.service.cryptutils;

import com.darkurfu.authservice.consts.PrivateConst;
import com.darkurfu.authservice.datamodels.session.PairRtJwt;
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
                PrivateConst.getSecretJwtKey()
        );
    }

    private SecretKey getSigningKey(String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public PairRtJwt generatePair(String role, long userId, String sessionId){
        String rt = generateRefreshJWT(userId, sessionId);
        String jwt = generateAccessJWT(role, userId, sessionId);

        return new PairRtJwt(rt,jwt);
    }

    public  String generateAccessJWT(String role, long userId, String sessionId){
        String jwt;

        LocalDateTime date = LocalDateTime.now(ZoneId.of("Asia/Yekaterinburg"));

        jwt = Jwts.builder()
                .header()
                .type("AccessJWT")
                .add("alg", secretKey.getAlgorithm())

                .and()
                .claim("role", role) // moder, user or smth else

                .claim("userId", userId)
                .claim("sessionId", sessionId)

                .issuedAt(
                        Date.from(date.atZone(ZoneId.of("Asia/Yekaterinburg")).toInstant())) //date of generate token
                .expiration(
                        Date.from(date.plusHours(2).atZone(ZoneId.of("Asia/Yekaterinburg")).toInstant())) // date of end token


                .signWith(secretKey) //set secret key
                .compact();

        return jwt;
    }

    public  String generateRefreshJWT(long userId, String sessionId){
        String jwt;

        LocalDate date = LocalDate.now(ZoneId.of("Asia/Yekaterinburg"));

        jwt = Jwts.builder()
                .header()
                .type("RefreshJWT")
                .add("alg", secretKey.getAlgorithm())

                .and()
                .claim("userId", userId)
                .claim("sessionId", sessionId)

                .issuedAt(
                        Date.from(date.atStartOfDay(ZoneId.of("Asia/Yekaterinburg")).toInstant())) //date of generate token
                .expiration(
                        Date.from(date.plusDays(7).atStartOfDay(ZoneId.of("Asia/Yekaterinburg")).toInstant())) // date of end token


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

