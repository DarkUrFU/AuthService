package com.darkurfu.authservice.datamodels.mod_api;

import com.darkurfu.authservice.datamodels.enums.Services;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.HashMap;

@Data
@AllArgsConstructor
public class ModeratorInfo {
    private Long id;
    private Long chatId;
    private String tgName;

    private Short modService;
    private Short eventService;
    private Short authService;
    private Short userService;
    private Short teamService;
    private Short baseService;


    public HashMap<Integer, Integer> getPermissions() throws IllegalAccessException, NoSuchFieldException {

        HashMap<Integer, Integer> permissions = new HashMap<>();


        for (Services service: Services.values()) {
            Field field = this.getClass().getDeclaredField(service.programName);
            field.setAccessible(true);
            Short value = (Short) field.get(this);

            permissions.put(
                    service.code.intValue(),
                    value.intValue()
            );
        }


        return permissions;
    }


    /*
    public HashMap<Integer, Integer> getPermissions() throws InstantiationException, IllegalAccessException {

        HashMap<Integer, Integer> permissions = new HashMap<>();
        Field[] allFields = ModeratorInfo.class.getDeclaredFields();

        for (Field field : allFields) {
            Class<?> targetType = field.getType();
            Object objectValue = targetType.newInstance();
            Object value = field.get(objectValue);
            permissions.put(field.getName(), value);
        }

        return permissions;
    }*/
}
