package com.darkurfu.authservice.repository.session;

import com.darkurfu.authservice.datamodels.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {

    @Query("select s.statusCode from session s where s.id = ?1")
    Optional<Short> getStatus(UUID uuid);


    @Modifying
    @Query("update session s set s.statusCode = :status where  s.id = :uuid")
    void setStatus(@Param("uuid") UUID uuid, @Param("status") Short status);



    @Query("select s from session s where s.userId = ?1")
    Optional<Short> getAllByUserId(UUID uuid);

    @Modifying
    @Query("update session s set s.statusCode = :status where s.userId = :uuid")
    void setStatusForAllByUserId(@Param("uuid") UUID uuid, @Param("status") Short status);
}
