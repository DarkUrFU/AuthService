package com.darkurfu.authservice.datamodels.enums;

import com.darkurfu.authservice.datamodels.exceptions.NotFindTypeException;
import lombok.Getter;

@Getter
public enum SessionStatus {
    ACTIVE((short) 0), CLOSE((short) 1), LOGOUT((short) 2), DEAD((short) 3);


    private final short code;

    SessionStatus(short code){
        this.code = code;
    }

    public SessionStatus getByCode(short code) throws NotFindTypeException {
        try {

            return SessionStatus.values()[code];

        } catch (Exception e){
            throw new NotFindTypeException(String.valueOf(code), this);
        }

    }
}
