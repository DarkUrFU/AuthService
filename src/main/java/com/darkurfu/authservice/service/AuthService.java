package com.darkurfu.authservice.service;

import com.darkurfu.authservice.datamodels.exceptions.*;
import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.datamodels.session.SessionLoginInfo;
import com.darkurfu.authservice.datamodels.user.User;
import com.darkurfu.authservice.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class AuthService {

    private final UserService userService;
    private final SessionService sessionService;
    private final BanService banService;


    @Autowired
    public AuthService(UserService userService, SessionService sessionService, BanService banService){
        this.userService = userService;
        this.sessionService = sessionService;
        this.banService = banService;
    }

    //@Transactional //не надо, т.к. по-умолчанию автокоммит включен, а тут только 1 запрос к бд
    public void registerUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        userService.register(user);
    }

    @Transactional
    public PairRtJwt login(User user, SessionLoginInfo sessionLoginInfo) throws NoSuchAlgorithmException, InvalidKeySpecException, BadPasswordOrLoginException, BadRoleException, NotFindUserException, BanActiveException {
        User usr = userService.login(user);

        try {
            banService.getByUserId(user.getId());
        } catch (NotFindBanException e) {
            return sessionService.createSession(usr, sessionLoginInfo);
        }

        throw new BanActiveException();
    }


    public void logout(String sessionId) throws NotFindSessionException, SessionNotActiveException {
        sessionService.logoutSession(sessionId);
    }
}
