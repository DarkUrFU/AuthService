package com.darkurfu.authservice.service;

import com.darkurfu.authservice.datamodels.enums.SessionStatus;
import com.darkurfu.authservice.datamodels.enums.UserType;
import com.darkurfu.authservice.exceptions.BadRoleException;
import com.darkurfu.authservice.exceptions.NotFindSessionException;
import com.darkurfu.authservice.exceptions.NotFindTypeException;
import com.darkurfu.authservice.exceptions.SessionNotActiveException;
import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.datamodels.session.Session;
import com.darkurfu.authservice.datamodels.session.SessionInfo;
import com.darkurfu.authservice.datamodels.session.SessionLoginInfo;
import com.darkurfu.authservice.datamodels.user.User;
import com.darkurfu.authservice.datamodels.user.UserAuthInfo;
import com.darkurfu.authservice.repository.mod.ModRepository;
import com.darkurfu.authservice.repository.session.SessionInfoRepository;
import com.darkurfu.authservice.repository.session.SessionLoginInfoRepository;
import com.darkurfu.authservice.repository.session.SessionRepository;
import com.darkurfu.authservice.service.cryptutils.JWTUtil;
import com.darkurfu.authservice.service.system.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {

    private final ModRepository modRepository;
    private final JWTUtil jwtUtil;
    private final SessionRepository sessionRepository;
    private final SessionInfoRepository sessionInfoRepository;
    private final SessionLoginInfoRepository sessionLoginInfoRepository;

    @Autowired
    public SessionService(JWTUtil jwtUtil, ModRepository modRepository,
                          SessionRepository sessionRepository, SessionInfoRepository sessionInfoRepository,
                          SessionLoginInfoRepository sessionLoginInfoRepository){
        this.jwtUtil = jwtUtil;
        this.modRepository = modRepository;
        this.sessionRepository = sessionRepository;
        this.sessionInfoRepository = sessionInfoRepository;
        this.sessionLoginInfoRepository = sessionLoginInfoRepository;
    }

    @Transactional // он не понмал что в транзакции оборачивать
    public PairRtJwt createSession(User user, SessionLoginInfo sessionLoginInfo) throws BadRoleException {
        UUID uuid = UUID.randomUUID();
        UserAuthInfo userAuthInfo = new UserAuthInfo(
                uuid,
                user.getId(),
                (int) user.getType()
        );

        try {
            if (UserType.getByCode(user.getType()) != UserType.USER){
                userAuthInfo.setPermissions(
                        modRepository.getAccessFor(user.getId()).getPermissions()
                );
            }
        } catch (NotFindTypeException e){
            throw new BadRoleException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        PairRtJwt pairRtJwt = jwtUtil.generatePair(userAuthInfo);

        sessionRepository.save(new Session(uuid, user.getId(), SessionStatus.ACTIVE.code));

        sessionLoginInfo.setId(uuid);
        sessionLoginInfo.setLastActiveTime(TimeUtil.getCurrentTime());

        sessionLoginInfoRepository.save(sessionLoginInfo);

        return pairRtJwt;
    }

    public SessionStatus getSessionStatus(String uuid) throws NotFindTypeException, NotFindSessionException {
        Optional<Short> sessionInfo = sessionRepository.getStatus(UUID.fromString(uuid));

        if (sessionInfo.isPresent()){
            return SessionStatus.ACTIVE.getByCode(sessionInfo.get());
        }

        throw new NotFindSessionException();
    }

    public SessionInfo getSessionInfo(String id) throws NotFindSessionException {
        Optional<SessionInfo> sessionInfo = sessionInfoRepository.findById(UUID.fromString(id));

        if (sessionInfo.isPresent()){
            return sessionInfo.get();
        }

        throw new NotFindSessionException();
    }

    public List<SessionInfo> getUserSessions(UUID id) throws NotFindSessionException {
        List<SessionInfo> sessionsInfo = sessionInfoRepository.findSessionInfoByUserId(id);

        if (!sessionsInfo.isEmpty()){
            return sessionsInfo;
        }

        throw new NotFindSessionException();
    }

    /**
     * Обновление статуса сессии
     *
     * @param id session id
     * @param newStatus new session status
     * @throws NotFindSessionException не нашёл сессию
     * @throws SessionNotActiveException сессия не активна
     */
    private void setSessionStatus(String id, SessionStatus newStatus) throws NotFindSessionException, SessionNotActiveException {

        Optional<Short> status = sessionRepository.getStatus(UUID.fromString(id));
        if (status.isPresent()){

            if (status.get().equals(SessionStatus.ACTIVE.code)){
                sessionRepository.setStatus(UUID.fromString(id), newStatus.code);
                return;
            }

            throw new SessionNotActiveException();
        }

        throw new NotFindSessionException();
    }

    public void closeSession(String id) throws NotFindSessionException, SessionNotActiveException {
        setSessionStatus(id, SessionStatus.CLOSE);
    }

    public void logoutSession(String id) throws SessionNotActiveException, NotFindSessionException {
        setSessionStatus(id, SessionStatus.LOGOUT);
    }

    public void killSession(String id) throws SessionNotActiveException, NotFindSessionException {
        setSessionStatus(id, SessionStatus.DEAD);
    }

    public void killAllSessions(UUID userId) {
        sessionRepository.setStatusForAllByUserId(userId, SessionStatus.DEAD.code);
    }
}
