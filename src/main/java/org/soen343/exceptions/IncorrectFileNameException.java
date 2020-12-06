package org.soen343.exceptions;

    public class IncorrectFileNameException extends Exception {

        public IncorrectFileNameException(String fileName) {
            super("Incorrect fileName: " + fileName);
        }
    }