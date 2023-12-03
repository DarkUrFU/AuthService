package com.darkurfu.authservice.service;

import com.darkurfu.authservice.exceptions.BadPasswordOrLoginException;
import com.darkurfu.authservice.exceptions.NotFindUserException;
import com.darkurfu.authservice.datamodels.user.UpdateUserPassword;
import com.darkurfu.authservice.datamodels.user.User;
import com.darkurfu.authservice.repository.user.UserRepository;
import com.darkurfu.authservice.service.cryptutils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;
import java.util.UUID;

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
        user.setId(UUID.randomUUID());

        userRepository.save(user);
    }

    @Transactional
    public User login(User user) throws NoSuchAlgorithmException, InvalidKeySpecException, BadPasswordOrLoginException, NotFindUserException {
        Optional<User> usr = userRepository.findByLogin(user.getLogin());

        if (usr.isPresent()){
            String passwd = hashUtil.generateHash(user.getPassword(), usr.get().getSalt());

            if (usr.get().getPassword().equals(passwd)){
                return usr.get();
            } else {
                throw new BadPasswordOrLoginException();
            }
        }

        throw new NotFindUserException();
    }


    @Transactional
    public void updatePassword(UpdateUserPassword updateUserPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Optional<User> usr = userRepository.findById(updateUserPassword.getId());

        if (usr.isPresent()) {
            User user = usr.get();

            if (user.getPassword().equals(hashUtil.generateHash(updateUserPassword.getOldPassword(), user.getSalt()))){

                user.setPassword(hashUtil.generateHash(updateUserPassword.getNewPassword(), user.getSalt()));
                userRepository.save(user);
            }
        }
    }

    public UUID getIdByLogin(String login) throws NotFindUserException {
        Optional<UUID> uuid = userRepository.findIdByLogin(login);

        if (uuid.isPresent()){
            return uuid.get();
        }

        throw new NotFindUserException();
    }
}
