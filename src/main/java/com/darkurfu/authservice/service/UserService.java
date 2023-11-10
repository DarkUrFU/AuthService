package com.darkurfu.authservice.service;

import com.darkurfu.authservice.datamodels.exceptions.BadPasswordOrLogin;
import com.darkurfu.authservice.datamodels.user.User;
import com.darkurfu.authservice.repository.user.UserRepository;
import com.darkurfu.authservice.service.cryptutils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        String salt = hashUtil.generateSaltAsString();

        user.setSalt(salt);

        user.setPassword(hashUtil.generateHash(user.getPassword(), salt));

        userRepository.save(user);

    }

    public User login(User user) throws NoSuchAlgorithmException, InvalidKeySpecException, BadPasswordOrLogin {
        User usr = userRepository.findByLogin(user.getLogin());
        String passwd = hashUtil.generateHash(user.getPassword(), usr.getSalt());

        if (usr.getPassword().equals(passwd)){
            return usr;
        } else {
            throw new BadPasswordOrLogin();
        }

    }
}
