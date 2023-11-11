package com.darkurfu.authservice.controller;

import com.darkurfu.authservice.datamodels.exceptions.BadPasswordOrLoginException;
import com.darkurfu.authservice.datamodels.exceptions.LoginUsedException;
import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.datamodels.user.User;
import com.darkurfu.authservice.datamodels.user.UserLogin;
import com.darkurfu.authservice.service.AuthService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }


    /**
     * Регистрация новых пользователей
     *
     * @return JWT
     */
    @PostMapping("/register")
    ResponseEntity<Object> registerUser(
            @RequestBody User user
            ){
        ResponseEntity<Object> response;

        try {
            authService.registerUser(user);

            response = new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
        }   /* catch (PSQLException e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }*/ catch (DataIntegrityViolationException e) {
            response = new ResponseEntity<>("Bad login", HttpStatusCode.valueOf(400));
        } catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }

        return response;
    }


    /**
     * Авторизация
     *
     * @param userLogin
     * @return
     */
    @PostMapping("/login")
    ResponseEntity<Object> loginUser(
            @RequestBody UserLogin userLogin
            ){
        ResponseEntity<Object> response;

        try {
            PairRtJwt pairRtJwt = authService.login(userLogin.parseUser(), userLogin.parsSessionLoginInfo());

            response = new ResponseEntity<>(pairRtJwt, HttpStatusCode.valueOf(200));
        }  catch (BadPasswordOrLoginException e){

            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        } catch (Exception e){

            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }

        return response;
    }
}
