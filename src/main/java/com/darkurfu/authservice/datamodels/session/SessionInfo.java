package com.darkurfu.authservice.datamodels.session;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "session")
@SecondaryTable(name = "session_login_info", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class SessionInfo {

    @Id
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;
    private short status;


    @Column(table = "session_login_info")
    private String ip;

    @Column(table = "session_login_info")
    private String device;

    @Column(name = "last_active_time", table = "session_login_info")
    private Timestamp lastActiveTime;

    public SessionInfo(){}


}
