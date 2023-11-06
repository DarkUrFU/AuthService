package com.darkurfu.authservice.datamodels.enums;

import com.darkurfu.authservice.datamodels.exceptions.NotFindTypeException;
import lombok.Getter;

@Getter
public enum UserType {
    USER((short) 0), MODERATOR((short) 1);


    private final short code;

    UserType(short code){
        this.code = code;
    }

    public UserType getByCode(short code) throws NotFindTypeException {
        try {

            return UserType.values()[code];

        } catch (Exception e){
            throw new NotFindTypeException(String.valueOf(code), this);
        }

    }
}
