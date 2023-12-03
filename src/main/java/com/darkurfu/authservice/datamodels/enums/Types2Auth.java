package com.darkurfu.authservice.datamodels.enums;

import com.darkurfu.authservice.exceptions.NotFindTypeException;
import lombok.Getter;


@Getter
public enum Types2Auth {
    TELEGRAM((short) 0), EMAIL((short) 1);

    private final short code;


    Types2Auth(short code){
        this.code = code;
    }



    public Types2Auth getByCode(short code) throws NotFindTypeException {
        try {

            return Types2Auth.values()[code];

        } catch (Exception e){
            throw new NotFindTypeException(String.valueOf(code), this);
        }

    }
}
