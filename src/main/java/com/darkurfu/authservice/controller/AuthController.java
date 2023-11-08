package com.darkurfu.authservice.controller;

import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.datamodels.session.SessionLoginInfo;
import com.darkurfu.authservice.datamodels.user.User;
import com.darkurfu.authservice.datamodels.user.UserLogin;
import com.darkurfu.authservice.service.AuthService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        } catch (Exception e){

            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }

        return response;
    }
}
