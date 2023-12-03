package com.darkurfu.authservice.controller.web;

import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwt")
public class JWTController {

    private final SessionService sessionService;

    @Autowired
    public JWTController(SessionService sessionService){
        this.sessionService = sessionService;
    }


    /**
     * Получение нового JWT взамен устаревшего
     *
     * @return JWT
     */
    @GetMapping("/update")
    ResponseEntity<String> updateToken(
            @RequestBody PairRtJwt pairRtJwt
            ){
        ResponseEntity<String> response = new ResponseEntity<>("", HttpStatusCode.valueOf(200));


        return response;
    }

}
