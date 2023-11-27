package com.darkurfu.authservice.service.authutils;

import com.darkurfu.authservice.datamodels.enums.Permissions;
import com.darkurfu.authservice.datamodels.enums.Services;
import com.darkurfu.authservice.datamodels.exceptions.NotFindTypeException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrantedAuthUtil {
    public static String getAuthPermissionStr(Services service, Permissions permission){
        return String.format("%s:%s",
                service.serviceName,
                permission.permissionName
        );
    }

    public static String getAuthPermissionStr(Integer service, Integer permission) throws NotFindTypeException {
        return String.format("%s:%s",
            Services.getByCode(service).serviceName,
            Permissions.getByCode(permission).permissionName
        );
    }

    public static List<SimpleGrantedAuthority> getAuthorities(HashMap<Integer, Integer> permissions) throws NotFindTypeException {
        List<SimpleGrantedAuthority>  authorities = new ArrayList<>();

        for (Map.Entry<Integer, Integer> permission : permissions.entrySet() ) {
            authorities.add(
                    new SimpleGrantedAuthority(
                            String.format("%s:%s",
                                    Services.getByCode(permission.getKey()).serviceName,
                                    Permissions.getByCode(permission.getValue()).permissionName
                            )

                    )
            );
        }

        return authorities;
    }
}
