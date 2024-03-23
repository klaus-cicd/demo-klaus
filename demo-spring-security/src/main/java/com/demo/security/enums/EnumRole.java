package com.demo.security.enums;

public enum EnumRole {

    ADMIN("1"),
    SUPPLIER("2"),
    SELLER("3"),
    BUYER("4");

    private final String type;

    EnumRole(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
