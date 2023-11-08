package com.darkurfu.authservice.repository.user;

import com.darkurfu.authservice.datamodels.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from users u where u.login = ?1")
    User findByLogin(String login);
}
