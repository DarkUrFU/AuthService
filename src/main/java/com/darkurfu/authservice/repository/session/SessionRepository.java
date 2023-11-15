package com.darkurfu.authservice.repository.session;

import com.darkurfu.authservice.datamodels.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {

    
    @Query("select s.statusCode from session s where s.id = ?1")
    short getStatus(UUID uuid);
    
}
