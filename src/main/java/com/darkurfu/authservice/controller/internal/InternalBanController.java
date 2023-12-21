package com.darkurfu.authservice.controller.internal;

import com.darkurfu.authservice.datamodels.Ban;
import com.darkurfu.authservice.exceptions.BanActiveException;
import com.darkurfu.authservice.exceptions.NotFindBanException;
import com.darkurfu.authservice.exceptions.NotFindUserException;
import com.darkurfu.authservice.service.BanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name="Внутреннее управление блокировками", description="Описание контролера")
@RestController
@RequestMapping("/api/internal/v1/auth/ban")
public class InternalBanController {

    private final BanService banService;

    @Autowired
    public InternalBanController(BanService banService){
        this.banService = banService;
    }


    @Operation(
            summary = "Блокировка пользователя",
            description = "Позволяет заблокировать пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "success", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = Ban.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Ban Active"),
                    @ApiResponse(responseCode = "500", description = "server error")
            }
    )
    @PostMapping("/")
    ResponseEntity<String> createBan(
            @RequestBody Ban ban
    ) {
        ResponseEntity<String> response;

        try {
            banService.create(ban);
            response = new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
        } catch (BanActiveException e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(404));
        } catch (Exception e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(500));
        }

        return response;
    }


    @Operation(
            summary = "Снятие блокировки с пользователя по id",
            description = "Позволяет заблокировать пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "success"),
                    @ApiResponse(responseCode = "404", description = "Nit find ban"),
                    @ApiResponse(responseCode = "500", description = "server error")
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteBan(
            @PathVariable String id
    ) {
        ResponseEntity<String> response;

        try {
            banService.delete(UUID.fromString(id));
            response = new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
        } catch (NotFindBanException e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(404));
        } catch (Exception e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(500));
        }

        return response;
    }

    @Operation(
            summary = "Снятие блокировки с пользователя по логину пользователя",
            description = "Позволяет заблокировать пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "success"),
                    @ApiResponse(responseCode = "404", description = "Not find user"),
                    @ApiResponse(responseCode = "500", description = "server error")
            }
    )
    @DeleteMapping("/user/login/{login}")
    ResponseEntity<String> deleteBanByUserLogin(
            @PathVariable String login
    ) {
        ResponseEntity<String> response;

        try {
            banService.deleteByUserLogin(login);
            response = new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
        } catch (NotFindUserException | NotFindBanException e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(404));
        } catch (Exception e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(500));
        }

        return response;
    }

    @Operation(
            summary = "Снятие блокировки с пользователя по id пользователя",
            description = "Позволяет заблокировать пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "success"),
                    @ApiResponse(responseCode = "404", description = "Not find user"),
                    @ApiResponse(responseCode = "500", description = "server error")
            }
    )
    @DeleteMapping("/user/id/{id}")
    ResponseEntity<String> deleteBanByUserId(
            @PathVariable String id
    ) {
        ResponseEntity<String> response;

        try {
            banService.deleteByUserId(UUID.fromString(id));
            response = new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
        } catch (NotFindUserException | NotFindBanException e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(404));
        } catch (Exception e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(500));
        }

        return response;
    }

    @Operation(
            summary = "Получить информацию о блокировке пользователя по id",
            description = "Позволяет заблокировать пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "success", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = Ban.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not find ban"),
                    @ApiResponse(responseCode = "500", description = "server error")
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<Object> getBan(
            @PathVariable String id
    ) {
        ResponseEntity<Object> response;

        try {
            Ban ban = banService.get(UUID.fromString(id));
            response = new ResponseEntity<>(ban, HttpStatusCode.valueOf(200));
        } catch (NotFindBanException e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(404));
        } catch (Exception e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(500));
        }

        return response;
    }

    @Operation(
            summary = "Получить информацию о блокировке пользователя по логину пользователя",
            description = "Позволяет заблокировать пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "success", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = Ban.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not find user"),
                    @ApiResponse(responseCode = "500", description = "server error")
            }
    )
    @GetMapping("/user/login/{login}")
    ResponseEntity<Object> getBanByUserLogin(
            @PathVariable String login
    ) {
        ResponseEntity<Object> response;

        try {
            Ban ban = banService.getByUserLogin(login);
            response = new ResponseEntity<>(ban, HttpStatusCode.valueOf(200));
        } catch (NotFindUserException | NotFindBanException e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(404));
        } catch (Exception e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(500));
        }


        return response;
    }

    @Operation(
            summary = "Получить информацию о блокировке пользователя по id пользователя",
            description = "Позволяет заблокировать пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "success", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = Ban.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not find ban"),
                    @ApiResponse(responseCode = "500", description = "server error")
            }
    )
    @GetMapping("/user/id/{id}")
    ResponseEntity<Object> getBanByUserId(
            @PathVariable String id
    ) {
        ResponseEntity<Object> response;

        try {
            Ban ban = banService.getByUserId(UUID.fromString(id));
            response = new ResponseEntity<>(ban, HttpStatusCode.valueOf(200));
        } catch (NotFindBanException e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(404));
        } catch (Exception e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(500));
        }


        return response;
    }
}
