package com.darkurfu.authservice.datamodels;

import lombok.Getter;
import lombok.NonNull;

public class PairRtJwt {

    @Getter
    private String rt;
    @Getter
    private String jwt;

    public PairRtJwt(@NonNull String rt, @NonNull String jwt){
        this.rt = rt;
        this.jwt = jwt;
    }
}
