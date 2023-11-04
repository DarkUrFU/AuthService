package com.darkurfu.authservice.datamodels;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Arrays;

@Data
@Entity()
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;

    protected User(){}

    public User(Long id, String login, String password){
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }


    public byte[] getSalt(){
        byte[] b = (login + password).getBytes();
        //b = Arrays.copyOfRange(b, 0, 16);
        return b;
    }

}
