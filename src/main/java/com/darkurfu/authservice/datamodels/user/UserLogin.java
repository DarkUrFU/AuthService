package com.darkurfu.authservice.datamodels.user;


import com.darkurfu.authservice.datamodels.session.SessionLoginInfo;
import lombok.Data;

@Data
public class UserLogin {
    private String login;
    private String password;
    private String ip;
    private String device;

    protected UserLogin(){}

    public UserLogin(String login, String password, String ip, String device){
        this.login = login;
        this.password = password;
        this.ip = ip;
        this.device = device;
    }

    public User parseUser(){
        return new User(this.login, this.password);
    }

    public SessionLoginInfo parsSessionLoginInfo(){
        return new SessionLoginInfo(this.ip, this.device);
    }
}

