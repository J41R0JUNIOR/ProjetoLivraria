package org.example;

public enum ClientOptions {
    LIST_BOOKS("1"),
    RENT_BOOKS("2"),
    RETURN_BOOKS("3"),
    REGISTER_BOOKS("4"),
    OUT("5");

    private String description;

    ClientOptions(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
