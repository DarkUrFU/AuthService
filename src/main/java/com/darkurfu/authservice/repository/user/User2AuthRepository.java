package com.darkurfu.authservice.repository.user;

import com.darkurfu.authservice.datamodels.user.User2Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User2AuthRepository extends JpaRepository<User2Auth, Long> {
}
