package com.darkurfu.authservice.service;

import com.darkurfu.authservice.datamodels.Ban;
import com.darkurfu.authservice.datamodels.exceptions.BanActiveException;
import com.darkurfu.authservice.datamodels.exceptions.NotFindBanException;
import com.darkurfu.authservice.datamodels.exceptions.NotFindUserException;
import com.darkurfu.authservice.repository.ban.BanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class BanService {

    private final BanRepository banRepository;
    private final UserService userService;

    @Autowired
    public BanService(BanRepository banRepository, UserService userService){
        this.banRepository = banRepository;
        this.userService = userService;
    }


    @Transactional
    public void create(Ban ban) throws BanActiveException {
        Optional<Ban> b = banRepository.findBanByUserId(ban.getUserId());

        if (b.isEmpty()){
            banRepository.save(ban);
            return;
        }

        throw new BanActiveException();
    }


    @Transactional
    public void delete(UUID uuid) throws NotFindBanException {
        Optional<Ban> b = banRepository.findById(uuid);

        if (b.isPresent()){
            banRepository.deleteBanById(uuid);
            return;
        }

        throw new NotFindBanException();
    }


    @Transactional
    public void deleteByUserId(UUID id) throws NotFindUserException, NotFindBanException {
        Optional<Ban> ban = banRepository.findBanByUserId(id);

        if (ban.isPresent()){
            banRepository.deleteBanByUserId(id);
            return;
        }

        throw new NotFindBanException();
    }


    @Transactional
    public void deleteByUserLogin(String login) throws NotFindUserException, NotFindBanException {
        UUID uuid = userService.getIdByLogin(login);

        deleteByUserId(uuid);
    }


    public Ban get(UUID uuid) throws NotFindBanException {
        Optional<Ban> ban = banRepository.findById(uuid);

        if (ban.isPresent()){
            return ban.get();
        }

        throw new NotFindBanException();
    }

    public Ban getByUserId(UUID uuid) throws NotFindBanException {
        Optional<Ban> ban = banRepository.findBanByUserId(uuid);

        if (ban.isPresent()){
            return ban.get();
        }

        throw new NotFindBanException();
    }

    @Transactional
    public Ban getByUserLogin(String login) throws NotFindBanException, NotFindUserException {
        UUID userId = userService.getIdByLogin(login);
        Optional<Ban> ban = banRepository.findById(userId);

        if (ban.isPresent()){
            return ban.get();
        }

        throw new NotFindBanException();
    }
}
