package com.darkurfu.authservice.datamodels.user;

import lombok.Data;

@Data
public class UpdateUserPassword {
    private Long id;
    private String oldPassword;
    private String newPassword;

    public UpdateUserPassword(){}

}
