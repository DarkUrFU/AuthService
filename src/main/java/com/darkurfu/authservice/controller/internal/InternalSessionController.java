package com.darkurfu.authservice.controller.internal;

import com.darkurfu.authservice.datamodels.Ban;
import com.darkurfu.authservice.datamodels.enums.SessionStatus;
import com.darkurfu.authservice.exceptions.NotFindTypeException;
import com.darkurfu.authservice.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Внутреннее управление сессиями", description="")
@RestController
@RequestMapping("/api/internal/v1/auth/session")
public class InternalSessionController {

    private final SessionService sessionService;

    @Autowired
    public InternalSessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }




    @Operation(
            summary = "Получить информацию статусе сессии по id",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "success"),
                    @ApiResponse(responseCode = "500", description = "server error")
            }
    )
    @GetMapping("/status/{id}")
    ResponseEntity<String> getSessionStatus(
            @PathVariable String id
    ) {
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
