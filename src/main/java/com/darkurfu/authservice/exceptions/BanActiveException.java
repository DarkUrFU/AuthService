package com.darkurfu.authservice.exceptions;


public class BanActiveException extends Exception {

    public BanActiveException(String uuid){
        super(String.format("Ban already create\nuuid: %s", uuid));
    }

    public BanActiveException(){
        super("Ban already create");
    }
}
