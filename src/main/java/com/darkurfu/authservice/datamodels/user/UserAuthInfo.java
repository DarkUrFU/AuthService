package com.darkurfu.authservice.datamodels.user;

import com.darkurfu.authservice.datamodels.enums.Permissions;
import com.darkurfu.authservice.datamodels.enums.Services;
import com.darkurfu.authservice.datamodels.enums.UserType;
import com.darkurfu.authservice.datamodels.exceptions.NotFindTypeException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@NoArgsConstructor
public class UserAuthInfo {
    private UUID sessionId;
    private UUID userId;
    private Integer role;
    private HashMap<Integer, Integer> permissions;

    public UserAuthInfo(UUID sessionId, UUID userId, Integer role, HashMap<Integer, Integer> permissions){
        this.sessionId = sessionId;
        this.userId = userId;
        this.role = role;
        this.permissions = permissions;
    }

    public UserAuthInfo(UUID sessionId, UUID userId, Integer role){
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
