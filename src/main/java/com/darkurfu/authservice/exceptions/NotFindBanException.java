package com.darkurfu.authservice.exceptions;


public class NotFindBanException extends Exception {

    public NotFindBanException(String uuid){
        super(String.format("Ban already deleted\nuuid: %s", uuid));
    }

    public NotFindBanException(){
        super("Ban already deleted");
    }
}
