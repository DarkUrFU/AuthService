package com.darkurfu.authservice.repository.session;

import com.darkurfu.authservice.datamodels.session.SessionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionInfoRepository extends JpaRepository<SessionInfo, UUID> {

    @Query("select si from SessionInfo si where si.userId = ?1")
    List<SessionInfo> findSessionInfoByUserId(Long id);
}
