package com.darkurfu.authservice.controller;

import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.datamodels.user.User;
import com.darkurfu.authservice.service.AuthService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

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
        } catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }

        return response;
    }


    /**
     * Авторизация
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    ResponseEntity<Object> loginUser(
            @RequestBody User user
    ){
        ResponseEntity<Object> response;

        try {
            PairRtJwt pairRtJwt = authService.login(user);

            response = new ResponseEntity<>(pairRtJwt, HttpStatusCode.valueOf(200));
        } catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }

        return response;
    }
}
