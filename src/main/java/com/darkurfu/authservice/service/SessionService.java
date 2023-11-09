package com.darkurfu.authservice.service;

import com.darkurfu.authservice.datamodels.enums.SessionStatus;
import com.darkurfu.authservice.datamodels.enums.UserType;
import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.datamodels.session.Session;
import com.darkurfu.authservice.datamodels.session.SessionInfo;
import com.darkurfu.authservice.datamodels.session.SessionLoginInfo;
import com.darkurfu.authservice.datamodels.user.User;
import com.darkurfu.authservice.repository.session.SessionLoginInfoRepository;
import com.darkurfu.authservice.repository.session.SessionRepository;
import com.darkurfu.authservice.service.cryptutils.HashUtil;
import com.darkurfu.authservice.service.cryptutils.JWTUtil;
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
    private final HashUtil hashUtil;
    private final SessionRepository sessionRepository;
    private final SessionLoginInfoRepository sessionLoginInfoRepository;

    @Autowired
    public SessionService(JWTUtil jwtUtil, HashUtil hashUtil,
                          SessionRepository sessionRepository,
                          SessionLoginInfoRepository sessionLoginInfoRepository){
        this.jwtUtil = jwtUtil;
        this.hashUtil = hashUtil;
        this.sessionRepository = sessionRepository;
        this.sessionLoginInfoRepository = sessionLoginInfoRepository;
    }

    public PairRtJwt createSession(User user, SessionLoginInfo sessionLoginInfo) throws NoSuchAlgorithmException, InvalidKeySpecException {
        UUID uuid = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now(ZoneId.of("Asia/Yekaterinburg"));
        PairRtJwt pairRtJwt = jwtUtil.generatePair(String.valueOf(user.getType()), user.getId(), uuid.toString());

        sessionRepository.save(new Session(uuid.toString(), user.getId(), SessionStatus.ACTIVE.getCode()));

        sessionLoginInfo.setId(uuid);
        sessionLoginInfo.setLastActiveTime(Timestamp.from(date.atZone(ZoneId.of("Asia/Yekaterinburg")).toInstant()));

        sessionLoginInfoRepository.save(sessionLoginInfo);

        return pairRtJwt;
    }
}
