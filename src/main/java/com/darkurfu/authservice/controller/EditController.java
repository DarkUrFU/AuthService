package com.darkurfu.authservice.controller;

import com.darkurfu.authservice.datamodels.user.User;
import com.darkurfu.authservice.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edit")
public class EditController {

    private RegisterService registerService;

    @Autowired
    public EditController(RegisterService registerService){
        this.registerService = registerService;
    }



    /**
     * Изменение пароля
     *
     * @return JWT
     */
    @PutMapping
    ResponseEntity<Object> updatePassword(){
        ResponseEntity<Object> response = new ResponseEntity<>("", HttpStatusCode.valueOf(200));


        return response;
    }
}
