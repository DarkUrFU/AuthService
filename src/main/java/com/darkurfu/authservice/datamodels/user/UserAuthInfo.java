package com.darkurfu.authservice.datamodels.user;

import com.darkurfu.authservice.datamodels.enums.Permissions;
import com.darkurfu.authservice.datamodels.enums.Services;
import com.darkurfu.authservice.datamodels.exceptions.NotFindTypeException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class UserAuthInfo {
    private String sessionId;
    private Long userId;
    private HashMap<Integer, Integer> permissions;

    public UserAuthInfo(String sessionId, Long userId, HashMap<Integer, Integer> permissions){
        this.sessionId = sessionId;
        this.userId = userId;
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() throws NotFindTypeException {
        List<SimpleGrantedAuthority>  authorities = new ArrayList<>();

        for (Map.Entry<Integer, Integer> permission : permissions.entrySet() ) {
            authorities.add(
                    new SimpleGrantedAuthority(
                            String.format("%s:%s", Services.getByCode(permission.getKey()), Permissions.getByCode(permission.getValue())))
            );
        }

        return authorities;
    }
}
