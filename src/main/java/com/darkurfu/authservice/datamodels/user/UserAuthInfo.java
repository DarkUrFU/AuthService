package com.darkurfu.authservice.datamodels.user;

import com.darkurfu.authservice.datamodels.enums.Permissions;
import com.darkurfu.authservice.datamodels.enums.Services;
import com.darkurfu.authservice.datamodels.enums.UserType;
import com.darkurfu.authservice.datamodels.exceptions.NotFindTypeException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class UserAuthInfo {
    private String sessionId;
    private Long userId;
    private Integer role;
    private HashMap<Integer, Integer> permissions;

    public UserAuthInfo(String sessionId, Long userId, Integer role, HashMap<Integer, Integer> permissions){
        this.sessionId = sessionId;
        this.userId = userId;
        this.role = role;
        this.permissions = permissions;
    }

    public UserAuthInfo(String sessionId, Long userId, Integer role){
        this.sessionId = sessionId;
        this.userId = userId;
        this.role = role;
        this.permissions = new HashMap<>();
    }

    public void setPermissionsOfMapStrInt(HashMap<String, Integer> hashMap){
        HashMap<Integer, Integer> newPermissions = new HashMap<>();
        for (Map.Entry<String, Integer> e: hashMap.entrySet()) {
            newPermissions.put(Integer.parseInt(e.getKey()), e.getValue());
        }
        this.permissions = newPermissions;
    }


}
