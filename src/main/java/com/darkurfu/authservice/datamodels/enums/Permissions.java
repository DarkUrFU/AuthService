package com.darkurfu.authservice.datamodels.enums;

import com.darkurfu.authservice.datamodels.exceptions.NotFindTypeException;

public enum Permissions {
    NOTHING((short) 0, "nothing"), READ((short) 1, "read"), COMMIT((short) 2, "update"), ALL((short) 3, "all");


    Permissions(short code, String name){}

    static public UserType getByCode(int code) throws NotFindTypeException {
        try {

            return UserType.values()[code];

        } catch (Exception e){
            throw new NotFindTypeException(String.valueOf(code));
        }

    }
}
