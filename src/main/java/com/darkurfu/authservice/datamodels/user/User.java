package com.darkurfu.authservice.datamodels.user;


import com.darkurfu.authservice.datamodels.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "users")
@SecondaryTable(name = "salt", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class User {
    @Id
    private UUID id;
    private String login;
    private String password;
    private short type;

    @Column(table = "salt")
    private String salt;

    protected User(){}

    public User(UUID id, String login, String password, short type){
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

}
