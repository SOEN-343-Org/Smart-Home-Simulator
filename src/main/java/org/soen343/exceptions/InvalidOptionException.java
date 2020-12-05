package org.soen343.exceptions;

public class InvalidOptionException extends Exception {

    public InvalidOptionException(String errorMessage){
        super(errorMessage);
    }

    public InvalidOptionException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
