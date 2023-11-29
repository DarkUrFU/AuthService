package com.darkurfu.authservice.controller.internal;

import com.darkurfu.authservice.datamodels.Ban;
import com.darkurfu.authservice.datamodels.exceptions.BanActiveException;
import com.darkurfu.authservice.datamodels.exceptions.NotFindBanException;
import com.darkurfu.authservice.datamodels.exceptions.NotFindUserException;
import com.darkurfu.authservice.service.BanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/internal/v1/auth/ban")
public class InternalBanController {

    private final BanService banService;

    @Autowired
    public InternalBanController(BanService banService){
        this.banService = banService;
    }

    /**
     * Создание блокировки
     *
     * @return JWT
     */
    @PostMapping("/")
    ResponseEntity<String> createBan(
            @RequestBody Ban ban
    ) {
        ResponseEntity<String> response;

        try {
            banService.create(ban);
            response = new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
        } catch (BanActiveException e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(409));
        } catch (Exception e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(500));
        }

        return response;
    }


    /**
     * Удаление блокировки
     *
     * @return JWT
     */
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteBan(
            @PathVariable String id
    ) {
        ResponseEntity<String> response;

        try {
            banService.delete(UUID.fromString(id));
            response = new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
        } catch (NotFindBanException e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(409));
        } catch (Exception e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(500));
        }

        return response;
    }

    /**
     * Удаление блокировки
     *
     * @return JWT
     */
    @DeleteMapping("/user/login/{login}")
    ResponseEntity<String> deleteBanByUserLogin(
            @PathVariable String login
    ) {
        ResponseEntity<String> response;

        try {
            banService.deleteByUserLogin(login);
            response = new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
        } catch (NotFindUserException | NotFindBanException e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(409));
        } catch (Exception e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(500));
        }

        return response;
    }

    /**
     * Удаление блокировки
     *
     * @return JWT
     */
    @DeleteMapping("/user/id/{id}")
    ResponseEntity<String> deleteBanByUserId(
            @PathVariable String id
    ) {
        ResponseEntity<String> response;

        try {
            banService.deleteByUserId(UUID.fromString(id));
            response = new ResponseEntity<>("success", HttpStatusCode.valueOf(200));
        } catch (NotFindUserException | NotFindBanException e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(409));
        } catch (Exception e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(500));
        }

        return response;
    }

    /**
     * Полученик информации
     *
     * @return JWT
     */
    @GetMapping("/{id}")
    ResponseEntity<Object> getBan(
            @PathVariable String id
    ) {
        ResponseEntity<Object> response;

        try {
            Ban ban = banService.get(UUID.fromString(id));
            response = new ResponseEntity<>(ban, HttpStatusCode.valueOf(200));
        } catch (NotFindBanException e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(409));
        } catch (Exception e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(500));
        }

        return response;
    }

    /**
     * снятие блокировки с сервера
     *
     * @return JWT
     */
    @GetMapping("/user/login/{login}")
    ResponseEntity<Object> getBanByUserLogin(
            @PathVariable String login
    ) {
        ResponseEntity<Object> response;

        try {
            Ban ban = banService.getByUserLogin(login);
            response = new ResponseEntity<>(ban, HttpStatusCode.valueOf(200));
        } catch (NotFindUserException | NotFindBanException e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(409));
        } catch (Exception e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(500));
        }


        return response;
    }

    /**
     * снятие блокировки с сервера
     *
     * @return JWT
     */
    @GetMapping("/user/id/{id}")
    ResponseEntity<Object> getBanByUserId(
            @PathVariable String id
    ) {
        ResponseEntity<Object> response;

        try {
            Ban ban = banService.getByUserId(UUID.fromString(id));
            response = new ResponseEntity<>(ban, HttpStatusCode.valueOf(200));
        } catch (NotFindBanException e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(409));
        } catch (Exception e) {
            response = new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatusCode.valueOf(500));
        }


        return response;
    }
}
