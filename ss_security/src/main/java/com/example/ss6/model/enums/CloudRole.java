package com.example.ss6.model.enums;

import com.example.ss6.entity.SecurityUser;

public enum CloudRole {

    ORG_MANAGER,
    EMPLOYEE,
    STORE_MANAGER,
    CUSTOMER;

    public static final String AUTHORITY_PREFIX = "ROLE_";

    public String getAuthorityName() {
        return AUTHORITY_PREFIX + name();
    }

    public static CloudRole getRoleByAuthorityName(String authorityName) {
        for (CloudRole role : CloudRole.values()) {
            if (role.getAuthorityName().equals(authorityName.toUpperCase())) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown authority [" + authorityName + "]");
    }

    public boolean inRole(SecurityUser user){
        return user.getRoles().contains(this);
    }

    public boolean notInRole(SecurityUser user){
        return !inRole(user);
    }

}
