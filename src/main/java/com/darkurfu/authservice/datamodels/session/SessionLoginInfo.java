package com.darkurfu.authservice.datamodels.session;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity(name = "session_login_info")
public class SessionLoginInfo {
    @Id
    private UUID id;

    private String ip;
    private String device;

    @Column(name = "last_active_time")
    private Timestamp lastActiveTime;

    protected SessionLoginInfo(){}

    public SessionLoginInfo(UUID id, String ip, String device, Timestamp lastActiveTime) {
        this.id = id;
        this.ip = ip;
        this.device = device;
        this.lastActiveTime = lastActiveTime;
    }

    public SessionLoginInfo(String id, String ip, String device, Timestamp lastActiveTime) {
        this.id = UUID.fromString(id);
        this.ip = ip;
        this.device = device;
        this.lastActiveTime = lastActiveTime;
    }

    public SessionLoginInfo(String ip, String device){
        this.ip = ip;
        this.device = device;
    }
}
