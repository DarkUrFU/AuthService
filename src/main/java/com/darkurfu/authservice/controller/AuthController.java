package com.darkurfu.authservice.controller;

import com.darkurfu.authservice.service.AuthService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping()
    ResponseEntity<String> registerUser(){
        ResponseEntity<String> response = new ResponseEntity<>("", HttpStatusCode.valueOf(200));


        return response;
    }
}
