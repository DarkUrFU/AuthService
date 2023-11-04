package com.darkurfu.authservice.datamodels.exceptions;


public class NotFindTypeException extends Exception {


    public NotFindTypeException(String message, Enum enumName){
        super(String.format("Not find Enum object in %s: %s", message, enumName));
    }

    public NotFindTypeException(String message){
        super(String.format("Not find Enum object: %s", message));
    }

    public NotFindTypeException(Enum enumName){
        super(String.format("Not find Enum object in %s", enumName.name()));
    }
    public NotFindTypeException(){
        super("Not find Enum object");
    }
}
