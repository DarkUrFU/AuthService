package com.darkurfu.authservice.datamodels.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "user_2auth")
public class User2Auth {

    @Id
    private Long id;
    private short type;
    private String address;

    protected User2Auth(){}

    public User2Auth(Long id, short type, String address){
        this.id = id;
        this.type = type;
        this.address = address;
    }

    public User2Auth(short type, String address){
        this.type = type;
        this.address = address;
    }
}
