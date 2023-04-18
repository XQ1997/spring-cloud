package com.x.base.exception;

public class XueChengException extends RuntimeException{

    private String errMessage;

    public XueChengException() {
        super();
    }

    public XueChengException(String message) {
        super(message);
    }

    public String getErrorMessage(){
        return errMessage;
    }

    public static void cast(String errMessage){
        throw new XueChengException();
    }

    public static void cast(CommonError commonError){
        throw new XueChengException(commonError.getErrMessage());
    }
}
