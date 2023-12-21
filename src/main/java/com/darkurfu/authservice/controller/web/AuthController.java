package com.darkurfu.authservice.controller.web;

import com.darkurfu.authservice.datamodels.Ban;
import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.datamodels.user.User;
import com.darkurfu.authservice.datamodels.user.UserAuthInfo;
import com.darkurfu.authservice.datamodels.user.UserLogin;
import com.darkurfu.authservice.exceptions.*;
import com.darkurfu.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name="Авторизация", description="Регистрация, вход и выход пользователя")
@RestController
@RequestMapping("/api/web/v1/auth/authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }


    @Operation(
            summary = "Регистрация новых пользователей",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "success"),
                    @ApiResponse(responseCode = "400", description = "bad login"),
                    @ApiResponse(responseCode = "500", description = "server error")
            }
    )
    @PostMapping("/register")
    ResponseEntity<String> registerUser(
            @RequestBody User user
            ){
        ResponseEntity<String> response;

        try {
            authService.registerUser(user);

            response = new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
        } catch (DataIntegrityViolationException e) {
            response = new ResponseEntity<>("bad login", HttpStatusCode.valueOf(400));
        } catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }

        return response;
    }


    @Operation(
            summary = "Вход для пользователя",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "success", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = PairRtJwt.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad password or login"),
                    @ApiResponse(responseCode = "406", description = "User has active ban"),
                    @ApiResponse(responseCode = "409", description = "Not find user"),
                    @ApiResponse(responseCode = "500", description = "server error")
            }
    )
    @PostMapping("/login")
    ResponseEntity<Object> loginUser(
            @RequestBody UserLogin userLogin
            ){
        ResponseEntity<Object> response;

        try {
            PairRtJwt pairRtJwt = authService.login(userLogin.parseUser(), userLogin.parsSessionLoginInfo());

            response = new ResponseEntity<>(pairRtJwt, HttpStatusCode.valueOf(200));
        }  catch (BadPasswordOrLoginException e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        } catch (NotFindUserException e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(409));
        } catch (BanActiveException e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(406));
        }catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }

        return response;
    }

    @Operation(
            summary = "Выход пользователя",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "success"),
                    @ApiResponse(responseCode = "404", description = "Not find session"),
                    @ApiResponse(responseCode = "409", description = "Session already is active"),
                    @ApiResponse(responseCode = "500", description = "server error")
            }
    )
    @PostMapping("/logout")
    ResponseEntity<Object> logoutUser(
    ){
        ResponseEntity<Object> response;
        UserAuthInfo userAuthInfo = (UserAuthInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            authService.logout(userAuthInfo.getSessionId().toString());
            response = new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
        } catch (NotFindSessionException e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        } catch (SessionNotActiveException e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(409));
        } catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }

        return response;
    }
}
