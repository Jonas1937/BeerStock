package com.jonas.testebeer.enums;

public enum BeerType {
    LAGER("lager");

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private BeerType(String description) {
        this.description = description;
    }
}
