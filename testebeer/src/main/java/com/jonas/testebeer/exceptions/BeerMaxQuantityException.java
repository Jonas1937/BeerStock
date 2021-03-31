package com.jonas.testebeer.exceptions;

public class BeerMaxQuantityException extends Exception{

    public BeerMaxQuantityException(String name, int max) {
        super(String.format("Max quantity for beer %s is %s", name, max));
    }
    
}
