package com.darkurfu.authservice.datamodels.enums;

import com.darkurfu.authservice.datamodels.exceptions.NotFindTypeException;
import lombok.Getter;

import java.lang.reflect.Field;

@Getter
public enum UserType {
    USER((short) 0), MODERATOR((short) 1), ADMIN((short) 2);


    public final short code;

    UserType(short code){
        this.code = code;
    }


    public static UserType getByCode(int code) throws NotFindTypeException {

        try {

            for (UserType u: UserType.values()) {
                Field field = u.getClass().getField("code");
                field.setAccessible(true);
                Short value = (Short) field.get(u);

                if (value.intValue() == code){
                    return u;
                }
            }

            throw new NotFindTypeException(String.valueOf(code));


        } catch (Exception e){
            throw new NotFindTypeException(String.valueOf(code));
        }

    }
}
