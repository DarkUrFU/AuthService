package com.darkurfu.authservice.service;

import com.darkurfu.authservice.consts.Api;
import com.darkurfu.authservice.datamodels.mod_api.ModeratorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ModService {

    RestTemplate restTemplate;

    @Autowired
    public ModService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public ModeratorInfo getAccessFor(long id) throws Exception {
        ResponseEntity<ModeratorInfo> response
                = restTemplate.getForEntity(Api.MOD_API_V1_GET_BY_ID(String.valueOf(id)), ModeratorInfo.class);

        if (response.getStatusCode() == HttpStatusCode.valueOf(200)){
            return response.getBody();
        } else {
            throw new Exception();
        }
    }
}
