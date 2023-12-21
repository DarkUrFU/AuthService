package com.darkurfu.authservice.controller.web;

import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.datamodels.user.UpdateUserPassword;
import com.darkurfu.authservice.datamodels.user.UserAuthInfo;
import com.darkurfu.authservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name="Изменение пользователей", description="")
@RestController
@RequestMapping("/api/web/v1/auth/edit")
public class EditController {

    private final UserService userService;

    @Autowired
    public EditController(UserService userService){
        this.userService = userService;
    }



    @Operation(
            summary = "Изменение пароля пользователя",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "success"),
                    @ApiResponse(responseCode = "403", description = "no access"),
                    @ApiResponse(responseCode = "500", description = "server error")
            }
    )
    @PutMapping
    ResponseEntity<Object> updatePassword(
            @RequestBody UpdateUserPassword updateUserPassword
            ){
        ResponseEntity<Object> response;
        UserAuthInfo userAuthInfo = (UserAuthInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userAuthInfo.getUserId().equals(updateUserPassword.getId())
        ){
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }


        try {
            userService.updatePassword(updateUserPassword);
            response = new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
        } catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }

        return response;
    }
}
