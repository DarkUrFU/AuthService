package com.darkurfu.authservice.repository;

import com.darkurfu.authservice.datamodels.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
