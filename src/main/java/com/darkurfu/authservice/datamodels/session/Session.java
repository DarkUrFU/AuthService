package com.darkurfu.authservice.datamodels.session;

import com.darkurfu.authservice.datamodels.enums.SessionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "session")
public class Session {

    @Id
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String rt;

    private short statusCode;


    protected Session(){}

    public Session(long id, long userId, String rt, short statusCode){
        this.id = id;
        this.userId = userId;
        this.rt = rt;
        this.statusCode = statusCode;
    }

    public Session(long id, long userId, String rt, SessionStatus statusCode){
        this.id = id;
        this.userId = userId;
        this.rt = rt;
        this.statusCode = statusCode.getCode();
    }
}
