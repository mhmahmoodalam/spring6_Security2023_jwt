package com.example.ss6.model.enums;

import com.example.ss6.entity.SecurityUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum CloudRole {

    ORG_MANAGER,
    EMPLOYEE,
    STORE_MANAGER,
    CUSTOMER;

    public static final String AUTHORITY_PREFIX = "ROLE_";

    public static CloudRole getRoleByAuthorityName(String authorityName) {
        for (CloudRole role : CloudRole.values()) {
            if (role.getAuthorityName().equals(authorityName.toUpperCase())) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown authority [" + authorityName + "]");
    }

    public String getAuthorityName() {
        return AUTHORITY_PREFIX + name();
    }

    public boolean inRole(SecurityUser user) {
        return user.getRoles().contains(this);
    }

    public boolean notInRole(SecurityUser user) {
        return !inRole(user);
    }

    public CloudRole parse(String cloudRole) {
        return  Enum.valueOf(CloudRole.class, cloudRole);
    }

    public  static Set<CloudRole> assignRolesByUser(UserType userType){
        Set<CloudRole> rolesToAdd = new HashSet<>(4);
        switch (userType){
            case CUSTOMER -> rolesToAdd.add(CUSTOMER);
            case EMPLOYEE -> rolesToAdd.add(EMPLOYEE);
            case MANAGER -> rolesToAdd.addAll(List.of(EMPLOYEE,STORE_MANAGER));
            case ORG_MANAGER -> rolesToAdd.addAll(List.of(EMPLOYEE,STORE_MANAGER,ORG_MANAGER));
        }
        return rolesToAdd;
    }

}
