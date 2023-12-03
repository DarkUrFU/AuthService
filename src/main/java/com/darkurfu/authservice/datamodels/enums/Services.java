package com.darkurfu.authservice.datamodels.enums;

import com.darkurfu.authservice.exceptions.NotFindTypeException;

import java.lang.reflect.Field;


public enum Services {
    AUTH_SERVICE((short) 0, "auth_service", "modService"),
    USER_SERVICE((short) 1, "user_service", "eventService"),
    MODERATOR_SERVICE((short) 2, "moderator_service", "authService"),
    TEAM_SERVICE((short) 3, "team_service", "userService"),
    EVENT_SERVICE((short) 4, "event_service", "teamService"),
    STUDENT_BASE_SERVICE((short) 5, "student_base_service", "baseService");


    public final String programName;
    public final String serviceName;
    public final Short code;

    Services(short code, String serviceName, String programName){
        this.code = code;
        this.serviceName = serviceName;
        this.programName = programName;
    }





    public static Services getByCode(int code) throws NotFindTypeException {

        try {

            for (Services u: Services.values()) {
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

    public static Services getByProgramName(String programName) throws NotFindTypeException {
        try {

            for (Services u: Services.values()) {
                Field field = u.getClass().getField("programName");
                field.setAccessible(true);
                String value = (String) field.get(u);

                if (value.equals(programName)){
                    return u;
                }
            }

            throw new NotFindTypeException(String.valueOf(programName));


        } catch (Exception e){
            throw new NotFindTypeException(String.valueOf(programName));
        }

    }
}
