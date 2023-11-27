package com.darkurfu.authservice.datamodels.exceptions;


public class NotFindSessionException extends Exception {

    public NotFindSessionException(String uuid){
        super(String.format("Not find session\nuuid: %s", uuid));
    }

    public NotFindSessionException(){
        super("Not find session");
    }
}
