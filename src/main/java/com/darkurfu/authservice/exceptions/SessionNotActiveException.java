package com.darkurfu.authservice.exceptions;


public class SessionNotActiveException extends Exception {

    public SessionNotActiveException(String uuid){
        super(String.format("Not find session\nuuid: %s", uuid));
    }

    public SessionNotActiveException(){
        super("Not find session");
    }
}
