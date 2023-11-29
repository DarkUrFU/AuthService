package com.darkurfu.authservice.controller.internal;

import com.darkurfu.authservice.datamodels.enums.SessionStatus;
import com.darkurfu.authservice.datamodels.exceptions.NotFindTypeException;
import com.darkurfu.authservice.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal/v1/auth/session")
public class InternalSessionController {

    private final SessionService sessionService;

    @Autowired
    public InternalSessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }




    /**
     * Получение сессий пользователя
     *
     * @param id session id
     * @return JWT
     */
    @GetMapping("/status/{id}")
    ResponseEntity<String> getSessionStatus(
            @PathVariable String id
    ) throws NotFindTypeException {
        ResponseEntity<String> response;

        try {
            SessionStatus sessionStatus = sessionService.getSessionStatus(id);
            response = new ResponseEntity<>(String.valueOf(sessionStatus.getCode()), HttpStatusCode.valueOf(200));

        } catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }

        return response;
    }
}
