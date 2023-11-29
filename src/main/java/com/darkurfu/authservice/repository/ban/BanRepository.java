package com.darkurfu.authservice.repository.ban;

import com.darkurfu.authservice.datamodels.Ban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BanRepository extends JpaRepository<Ban, UUID> {

    @Query("select b from bans b where b.userId = ?1")
    Optional<Ban> findBanByUserId(UUID id);

    @Modifying
    @Query("delete from bans b where b.userId = ?1")
    void deleteBanByUserId(UUID id);

    @Modifying
    @Query("delete from bans b where b.id = ?1")
    void deleteBanById(UUID id);
}
