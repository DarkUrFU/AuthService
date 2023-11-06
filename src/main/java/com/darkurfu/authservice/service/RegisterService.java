package com.darkurfu.authservice.service;

import com.darkurfu.authservice.datamodels.user.User;
import com.darkurfu.authservice.repository.UserRepository;
import com.darkurfu.authservice.service.cryptutils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final HashUtil hashUtil;

    @Autowired
    public RegisterService(HashUtil hashUtil, UserRepository userRepository){
        this.hashUtil = hashUtil;
        this.userRepository = userRepository;
    }

    @Transactional
    public void register(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {

        user.setPassword(hashUtil.generateHashWithSalt(user.getPassword(), user.getSalt()));

        userRepository.save(user);

    }


}
