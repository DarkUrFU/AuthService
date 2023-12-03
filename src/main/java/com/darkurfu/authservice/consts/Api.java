package com.darkurfu.authservice.consts;

public class Api {
    public final static String BASE_URL = "https://d5d6211r4dd23nu26ev1.apigw.yandexcloud.net";

    public final static String MOD_API_V1 = BASE_URL + "/api/internal/v1/mod/";
    public final static String MOD_API_V1_GET_ALL = MOD_API_V1 + "get/";
    public static String MOD_API_V1_GET_BY_ID(String id) {
        return MOD_API_V1 + "get/" + id;
    }


    
}
