package com.darkurfu.authservice.service;

import com.darkurfu.authservice.datamodels.user.User;
import com.darkurfu.authservice.service.cryptutils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class RegisterService {

    private final HashUtil hashUtil;
    private final JWTService jwtService;

    @Autowired
    public RegisterService(HashUtil hashUtil, JWTService jwtService){
        this.hashUtil = hashUtil;
        this.jwtService = jwtService;
    }

    @Transactional
    public String register(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {

        user.setPassword(hashUtil.generateHashWithSalt(user.getPassword(), user.getSalt()));

        return "";
    }


}
