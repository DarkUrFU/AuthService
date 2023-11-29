package com.darkurfu.authservice.controller.web;

import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.datamodels.user.User;
import com.darkurfu.authservice.datamodels.user.UserAuthInfo;
import com.darkurfu.authservice.datamodels.user.UserLogin;
import com.darkurfu.authservice.exceptions.*;
import com.darkurfu.authservice.service.AuthService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/web/v1/auth/authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }


    /**
     * Регистрация новых пользователей
     *
     * @return JWT
     */
    @PostMapping("/register")
    ResponseEntity<Object> registerUser(
            @RequestBody User user
            ){
        ResponseEntity<Object> response;

        try {
            authService.registerUser(user);

            response = new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
        } catch (DataIntegrityViolationException e) {
            response = new ResponseEntity<>("Bad login", HttpStatusCode.valueOf(400));
        } catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }

        return response;
    }


    /**
     * Авторизация
     *
     * @param userLogin
     * @return
     */
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

    /**
     * Закрытие сессии
     *
     * @return
     */
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
