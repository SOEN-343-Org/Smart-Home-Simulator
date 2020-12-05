package org.soen343.exceptions;

    public class IncorrectFileNameException extends Exception {

        public IncorrectFileNameException(String errorMessage) {
            super(errorMessage);
        }
    }