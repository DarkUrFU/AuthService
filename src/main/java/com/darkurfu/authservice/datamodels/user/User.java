package com.darkurfu.authservice.datamodels.user;


import com.darkurfu.authservice.datamodels.enums.UserType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private short type;

    protected User(){}

    public User(Long id, String login, String password, short type){
        this.id = id;
        this.login = login;
        this.password = password;
        this.type = type;
    }

    public User(String login, String password){
        this.login = login;
        this.password = password;
        this.type = UserType.USER.getCode();
    }


    public String getSalt(){
        return login + password;
    }

}
