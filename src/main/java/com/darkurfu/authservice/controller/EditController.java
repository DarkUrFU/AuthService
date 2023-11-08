package com.darkurfu.authservice.controller;

import com.darkurfu.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edit")
public class EditController {

    private UserService userService;

    @Autowired
    public EditController(UserService userService){
        this.userService = userService;
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
