package com.darkurfu.authservice.service;

import com.darkurfu.authservice.datamodels.exceptions.BadPasswordOrLogin;
import com.darkurfu.authservice.datamodels.user.User;
import com.darkurfu.authservice.repository.user.UserRepository;
import com.darkurfu.authservice.service.cryptutils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final HashUtil hashUtil;

    @Autowired
    public UserService(HashUtil hashUtil, UserRepository userRepository){
        this.hashUtil = hashUtil;
        this.userRepository = userRepository;
    }

    public void register(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {

        user.setPassword(hashUtil.generateHashWithSalt(user.getPassword(), user.getSalt()));

        userRepository.save(user);

    }

    public User login(User user) throws NoSuchAlgorithmException, InvalidKeySpecException, BadPasswordOrLogin {
        User usr = userRepository.findByLogin(user.getLogin());
        user.setPassword(hashUtil.generateHashWithSalt(user.getPassword(), user.getSalt()));

        if (usr.getPassword().equals(user.getPassword())){
            return usr;
        } else {
            throw new BadPasswordOrLogin();
        }

    }
}
