package com.darkurfu.authservice.datamodels.session;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "session")
@SecondaryTable(name = "session_login_info", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class SessionInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;
    private String rt;
    private short statusCode;


    private String ip;
    private String device;

    @Column(name = "last_active_time")
    private Timestamp lastActiveTime;
}
