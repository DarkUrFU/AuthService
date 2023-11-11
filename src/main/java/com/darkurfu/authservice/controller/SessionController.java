package com.darkurfu.authservice.controller;

import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    private SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }


    /**
     * Получение нового JWT взамен устаревшего
     *
     * @return JWT
     */
    @GetMapping
    ResponseEntity<String> updateJWT(
            @RequestBody PairRtJwt pairRtJwt
            ){
        ResponseEntity<String> response = new ResponseEntity<>("", HttpStatusCode.valueOf(200));





        return response;
    }

}
