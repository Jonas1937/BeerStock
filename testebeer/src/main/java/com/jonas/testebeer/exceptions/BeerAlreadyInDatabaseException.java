package com.jonas.testebeer.exceptions;

public class BeerAlreadyInDatabaseException extends Exception{
    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return "Beer already on Database";
    }
    
}
