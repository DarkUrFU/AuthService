package com.darkurfu.authservice.service;

import com.darkurfu.authservice.datamodels.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

@Service
public class RegisterService {

    private AuthService authService;
    private JWTService jwtService;

    @Autowired
    public RegisterService(AuthService authService, JWTService jwtService){
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @Transactional
    public String register(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {

        user.setPassword(authService.generateHash(user));

        return "";
    }


}
