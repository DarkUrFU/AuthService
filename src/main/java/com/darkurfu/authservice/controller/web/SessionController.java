package com.darkurfu.authservice.controller.web;

import com.darkurfu.authservice.exceptions.NotFindSessionException;
import com.darkurfu.authservice.exceptions.SessionNotActiveException;
import com.darkurfu.authservice.datamodels.session.SessionInfo;
import com.darkurfu.authservice.datamodels.user.UserAuthInfo;
import com.darkurfu.authservice.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/web/v1/auth/session")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }



    /**
     * Получение сессий пользователя
     *
     * @param id user id
     * @return JWT
     */
    @GetMapping("/all/{id}")
    ResponseEntity<Object> getUserSessions(
            @PathVariable Long id
            ) {
        ResponseEntity<Object> response;
        UserAuthInfo userAuthInfo = (UserAuthInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!userAuthInfo.getUserId().equals(id)){
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }

        List<SessionInfo> sessions = null;
        try {
            sessions = sessionService.getUserSessions(id);
            response = new ResponseEntity<>(sessions, HttpStatusCode.valueOf(404));
        } catch (NotFindSessionException e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        } catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }


        return response;
    }

    /**
     * Получение сессий пользователя
     *
     * @param id session id
     * @return JWT
     */
    @GetMapping("/info/{id}")
    ResponseEntity<Object> getSession(
            @PathVariable String id
    ){
        UserAuthInfo userAuthInfo = (UserAuthInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseEntity<Object> response;

        try {
            SessionInfo sessionInfo = sessionService.getSessionInfo(id);

            if (!userAuthInfo.getUserId().equals(sessionInfo.getUserId())){
                return new ResponseEntity<>(HttpStatusCode.valueOf(403));
            }

            response = new ResponseEntity<>(sessionInfo, HttpStatusCode.valueOf(200));
        } catch (NotFindSessionException e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        } catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }


        return response;
    }

    /**
     * Получение сессий пользователя
     *
     * @param id session id
     * @return JWT
     */
    @PostMapping("/close/{id}")
    ResponseEntity<String> closeSession(
            @PathVariable String id
    ){
        ResponseEntity<String> response;

        try {
            sessionService.closeSession(id);
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
