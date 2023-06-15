package com.example.ss6.model.enums;

public enum UserType {
    EMPLOYEE, MANAGER, CUSTOMER, ORG_MANAGER;

    public UserType parse(String usertype) {
        return  Enum.valueOf(UserType.class, usertype);
    }
}
