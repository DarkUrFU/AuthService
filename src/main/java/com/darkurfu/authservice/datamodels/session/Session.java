package com.darkurfu.authservice.datamodels.session;

import com.darkurfu.authservice.datamodels.enums.SessionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "session")
public class Session {

    @Id
    private UUID id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "status")
    private short statusCode;


    protected Session(){}

    public Session(UUID id, long userId, short statusCode){
        this.id = id;
        this.userId = userId;
        this.statusCode = statusCode;
    }

    public Session(String id, long userId, SessionStatus statusCode){
        this.id = UUID.fromString(id);
        this.userId = userId;
        this.statusCode = statusCode.getCode();
    }
}
