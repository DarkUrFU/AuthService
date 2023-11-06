package com.darkurfu.authservice.controller;

import com.darkurfu.authservice.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    private JWTService jwtService;

    @Autowired
    public SessionController(JWTService jwtService){
        this.jwtService = jwtService;
    }


    /**
     * Получение нового JWT взамен устаревшего
     *
     * @return JWT
     */
    @GetMapping
    ResponseEntity<String> updateJWT(){
        ResponseEntity<String> response = new ResponseEntity<>("", HttpStatusCode.valueOf(200));




        return response;
    }

}
