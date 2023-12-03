package com.darkurfu.authservice.datamodels.session;

import lombok.Getter;
import lombok.NonNull;

public record PairRtJwt(String rt, String jwt) {

    public PairRtJwt(@NonNull String rt, @NonNull String jwt) {
        this.rt = rt;
        this.jwt = jwt;
    }
}
