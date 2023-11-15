package com.darkurfu.authservice.service;

import com.darkurfu.authservice.datamodels.enums.SessionStatus;
import com.darkurfu.authservice.datamodels.enums.UserType;
import com.darkurfu.authservice.datamodels.exceptions.NotFindTypeException;
import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.datamodels.session.Session;
import com.darkurfu.authservice.datamodels.session.SessionInfo;
import com.darkurfu.authservice.datamodels.session.SessionLoginInfo;
import com.darkurfu.authservice.datamodels.user.User;
import com.darkurfu.authservice.repository.session.SessionLoginInfoRepository;
import com.darkurfu.authservice.repository.session.SessionRepository;
import com.darkurfu.authservice.service.cryptutils.HashUtil;
import com.darkurfu.authservice.service.cryptutils.JWTUtil;
import com.darkurfu.authservice.service.system.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class SessionService {

    private final JWTUtil jwtUtil;
    private final SessionRepository sessionRepository;
    private final SessionLoginInfoRepository sessionLoginInfoRepository;

    @Autowired
    public SessionService(JWTUtil jwtUtil,
                          SessionRepository sessionRepository,
                          SessionLoginInfoRepository sessionLoginInfoRepository){
        this.jwtUtil = jwtUtil;
        this.sessionRepository = sessionRepository;
        this.sessionLoginInfoRepository = sessionLoginInfoRepository;
    }

    @Transactional // он не понмал что в транзакции оборачивать
    public PairRtJwt createSession(User user, SessionLoginInfo sessionLoginInfo) {
        UUID uuid = UUID.randomUUID();
        PairRtJwt pairRtJwt = jwtUtil.generatePair(String.valueOf(user.getType()), user.getId(), uuid.toString());

        sessionRepository.save(new Session(uuid, user.getId(), SessionStatus.ACTIVE.getCode()));

        sessionLoginInfo.setId(uuid);
        sessionLoginInfo.setLastActiveTime(TimeUtil.getCurrentTime());

        sessionLoginInfoRepository.save(sessionLoginInfo);

        return pairRtJwt;
    }

    public SessionStatus getSessionStatus(String uuid) throws NotFindTypeException {

        return SessionStatus.ACTIVE.getByCode(sessionRepository.getStatus(UUID.fromString(uuid)));
    }
}
