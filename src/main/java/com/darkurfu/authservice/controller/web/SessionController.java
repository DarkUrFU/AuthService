package com.darkurfu.authservice.controller.web;

import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.exceptions.NotFindSessionException;
import com.darkurfu.authservice.exceptions.SessionNotActiveException;
import com.darkurfu.authservice.datamodels.session.SessionInfo;
import com.darkurfu.authservice.datamodels.user.UserAuthInfo;
import com.darkurfu.authservice.service.SessionService;
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

import java.util.List;
import java.util.UUID;

@Tag(name="Управление сессиями", description="")
@RestController
@RequestMapping("/api/web/v1/auth/session")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }



    @Operation(
            summary = "ПОлучение всех сессий пользователя по id пользователя",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "list of sessions", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = SessionInfo.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "no access"),
                    @ApiResponse(responseCode = "404", description = "not find session"),
                    @ApiResponse(responseCode = "500", description = "server error")
            }
    )
    @GetMapping("/all/{id}")
    ResponseEntity<Object> getUserSessions(
            @PathVariable UUID id
            ) {
        ResponseEntity<Object> response;
        UserAuthInfo userAuthInfo = (UserAuthInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!userAuthInfo.getUserId().equals(id)){
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }

        List<SessionInfo> sessions;
        try {
            sessions = sessionService.getUserSessions(id);
            response = new ResponseEntity<>(sessions, HttpStatusCode.valueOf(200));
        } catch (NotFindSessionException e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        } catch (Exception e){
            response = new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
        }


        return response;
    }

    @Operation(
            summary = "Получение информации о сессии пользователя по id",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "success", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = SessionInfo.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "no access"),
                    @ApiResponse(responseCode = "404", description = "not find session"),
                    @ApiResponse(responseCode = "500", description = "server error")
            }
    )
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

    @Operation(
            summary = "Закрытие сессии пользователя по id",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "success"),
                    @ApiResponse(responseCode = "404", description = "not find session"),
                    @ApiResponse(responseCode = "409", description = "session non active"),
                    @ApiResponse(responseCode = "500", description = "server error")
            }
    )
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
