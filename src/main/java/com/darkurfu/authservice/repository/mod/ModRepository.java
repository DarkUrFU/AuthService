package com.darkurfu.authservice.repository.mod;

import com.darkurfu.authservice.consts.Api;
import com.darkurfu.authservice.datamodels.mod.ModeratorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Repository
public class ModRepository {

    RestTemplate restTemplate;

    @Autowired
    public ModRepository(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public ModeratorInfo getAccessFor(UUID id) throws Exception {

        ResponseEntity<ModeratorInfo>
                response
                =
                restTemplate
                        .getForEntity(Api.MOD_API_V1_GET_BY_ID(id.toString()), ModeratorInfo.class);


        if (response.getStatusCode() == HttpStatusCode.valueOf(200) & response.hasBody()){
            return response.getBody();
        } else {
            throw new Exception();
        }
    }
}
