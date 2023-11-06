package com.darkurfu.authservice.service;

import com.darkurfu.authservice.datamodels.session.PairRtJwt;
import com.darkurfu.authservice.datamodels.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {


    //@Autowired
    public SessionService(){

    }

    public PairRtJwt createSession(User user) {
        PairRtJwt pairRtJwt = null;
        
        return pairRtJwt;        
    }
}
