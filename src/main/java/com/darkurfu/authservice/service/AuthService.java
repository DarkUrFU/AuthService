package com.darkurfu.authservice.service;

import com.darkurfu.authservice.datamodels.exceptions.BadPasswordOrLogin;
import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.datamodels.session.SessionLoginInfo;
import com.darkurfu.authservice.datamodels.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class AuthService {

    private final UserService userService;
    private final SessionService sessionService;


    @Autowired
    public AuthService(UserService userService, SessionService sessionService){
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Transactional
    public void registerUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        userService.register(user);
    }

    @Transactional
    public PairRtJwt login(User user, SessionLoginInfo sessionLoginInfo) throws NoSuchAlgorithmException, InvalidKeySpecException, BadPasswordOrLogin {
        User usr = userService.login(user);
        return sessionService.createSession(usr, sessionLoginInfo);
    }
}
