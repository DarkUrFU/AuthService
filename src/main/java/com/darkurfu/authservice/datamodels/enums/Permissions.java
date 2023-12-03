package com.darkurfu.authservice.datamodels.enums;

import com.darkurfu.authservice.exceptions.NotFindTypeException;

import java.lang.reflect.Field;


public enum Permissions {
    NOTHING((short) 0, "nothing"),
    READ((short) 1, "read"),
    COMMIT((short) 2, "update"),
    ALL((short) 3, "all");


    public final Short code;
    public final String permissionName;

    Permissions(short code, String permissionName){
        this.code = code;
        this.permissionName = permissionName;
    }


    public static Permissions getByCode(int code) throws NotFindTypeException {
        try {

            for (Permissions u: Permissions.values()) {
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
