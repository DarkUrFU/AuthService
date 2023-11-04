package com.darkurfu.authservice.datamodels.session;

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


    protected Session(){}

    public Session(long id, long userId, String rt){
        this.id = id;
        this.userId = userId;
        this.rt = rt;
    }
}
