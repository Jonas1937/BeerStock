package com.jonas.testebeer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BeerNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public BeerNotFoundException(String name) {
        super(String.format("Beer %s not found in the system", name));
    }   
    
}
