package org.soen343.exceptions;

public class InvalidOptionException extends Exception {

    public InvalidOptionException(Object top){
        super(top +  " is not in the valid option [window, door]");
    }
}
