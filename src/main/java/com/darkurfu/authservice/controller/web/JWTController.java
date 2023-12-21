package com.darkurfu.authservice.controller.web;

import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="Управление токенами", description="")
@RestController
@RequestMapping("/api/web/v1/auth/jwt")
public class JWTController {

    private final SessionService sessionService;

    @Autowired
    public JWTController(SessionService sessionService){
        this.sessionService = sessionService;
    }


    @Operation(
            summary = "Обновление токена",
            description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "success", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = PairRtJwt.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not find session"),
                    @ApiResponse(responseCode = "500", description = "server error")
            }
    )
    @GetMapping("/update")
    ResponseEntity<String> updateToken(
            @RequestBody PairRtJwt pairRtJwt
            ){
        ResponseEntity<String> response = new ResponseEntity<>("", HttpStatusCode.valueOf(200));


        return response;
    }

}
