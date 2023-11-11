package com.darkurfu.authservice.controller;

import com.darkurfu.authservice.datamodels.user.UpdateUserPassword;
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
    ResponseEntity<Object> updatePassword(
            @RequestHeader(name = "Authorization") String jwtAccess,
            @RequestBody UpdateUserPassword updateUserPassword
            ){
        ResponseEntity<Object> response;


        try {
            userService.updatePassword(updateUserPassword);
            response = new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
        } catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }

        return response;
    }
}
