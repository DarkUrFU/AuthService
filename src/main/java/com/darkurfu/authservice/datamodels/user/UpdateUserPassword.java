package com.darkurfu.authservice.datamodels.user;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateUserPassword {
    private UUID id;
    private String oldPassword;
    private String newPassword;

    public UpdateUserPassword(){}

}
