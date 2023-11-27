package com.darkurfu.authservice.service.cryptutils;

import com.darkurfu.authservice.consts.PrivateConst;
import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.datamodels.user.UserAuthInfo;
import com.darkurfu.authservice.service.system.TimeUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.*;
import java.util.Date;
import java.util.HashMap;


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

    public PairRtJwt generatePair(UserAuthInfo userAuthInfo){
        String rt = generateRefreshJWT(userAuthInfo.getUserId(), userAuthInfo.getSessionId());
        String jwt = generateAccessJWT(userAuthInfo.getRole(), userAuthInfo.getUserId(), userAuthInfo.getSessionId(), userAuthInfo.getPermissions());

        return new PairRtJwt(rt,jwt);
    }

    public  String generateAccessJWT(Integer role, Long userId, String sessionId, HashMap<Integer, Integer> permissions){
        String jwt;

        LocalDateTime date = LocalDateTime.now(ZoneId.of("Asia/Yekaterinburg"));

        jwt = Jwts.builder()
                .header()
                .type("AccessJWT")
                .add("alg", secretKey.getAlgorithm())

                .and()
                .claim("role", role) // moder, user or smth else
                .claim("permissions", permissions)

                .claim("userId", userId)
                .claim("sessionId", sessionId)

                .issuedAt(TimeUtil.getCurrentTime()) //date of generate token
                .expiration(TimeUtil.getCurrentTimePlusHours(2)) // date of end token


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

                .issuedAt(TimeUtil.getCurrentTime()) //date of generate token
                .expiration(TimeUtil.getCurrentTimePlusDays(7)) // date of end token


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

        return a;
    }


    public boolean isTokenValid(String jwt){

        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwt);
        } catch (SignatureException e){
            return false;
        }

        return true;
    }


    private boolean isTokenExpired(String token) {
        Date a = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();




        return false;
    }
}

