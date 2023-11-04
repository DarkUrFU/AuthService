package com.darkurfu.authservice.datamodels.enums;

import com.darkurfu.authservice.datamodels.exceptions.NotFindTypeException;
import lombok.Getter;

@Getter
public enum SessionType{
    ACTIVE((short) 0), CANCELLED((short) 1), LOGOUT((short) 2), DEAD((short) 3);


    private final short code;

    SessionType(short code){
        this.code = code;
    }

    public SessionType getByCode(short code) throws NotFindTypeException {
        try {

            return SessionType.values()[code];

        } catch (Exception e){
            throw new NotFindTypeException(String.valueOf(code), this);
        }

    }
}
