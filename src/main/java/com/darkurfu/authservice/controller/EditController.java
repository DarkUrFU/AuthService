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
     * Регистрация новых пользователей
     *
     * @return JWT
     */
    @PostMapping()
    ResponseEntity<String> registerUser(
            @RequestBody User user
            ){
        ResponseEntity<String> response;

        try {
            String a = registerService.register(user);
            response = new ResponseEntity<>(a, HttpStatusCode.valueOf(200));

        } catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }

        return response;
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
