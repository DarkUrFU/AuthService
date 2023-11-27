package com.darkurfu.authservice.service;

import com.darkurfu.authservice.datamodels.exceptions.BadPasswordOrLoginException;
import com.darkurfu.authservice.datamodels.exceptions.BadRoleException;
import com.darkurfu.authservice.datamodels.exceptions.LoginUsedException;
import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.datamodels.session.SessionLoginInfo;
import com.darkurfu.authservice.datamodels.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

@Service
public class AuthService {

    private final UserService userService;
    private final SessionService sessionService;


    @Autowired
    public AuthService(UserService userService, SessionService sessionService){
        this.userService = userService;
        this.sessionService = sessionService;
    }

    //@Transactional //не надо, т.к. по-умолчанию автокоммит включен, а тут только 1 запрос к бд
    public void registerUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        userService.register(user);
    }

    //@Transactional
    public PairRtJwt login(User user, SessionLoginInfo sessionLoginInfo) throws NoSuchAlgorithmException, InvalidKeySpecException, BadPasswordOrLoginException, BadRoleException {
        User usr = userService.login(user);
        return sessionService.createSession(usr, sessionLoginInfo);
    }
}
