package com.darkurfu.authservice.service;

import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.datamodels.user.User;
import io.jsonwebtoken.security.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class AuthService {

    private final RegisterService registerService;
    private final SessionService sessionService;


    @Autowired
    public AuthService(RegisterService registerService, SessionService sessionService){
        this.registerService = registerService;
        this.sessionService = sessionService;
    }

    public void registerUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        registerService.register(user);
    }

    public PairRtJwt login(User user) {
        PairRtJwt pairRtJwt = null;

        return pairRtJwt;
    }
}
