package com.example.ss6.model.enums;

public enum AccountState {
    ACTIVE, PENDING, REGISTRATION_REJECTED, DISABLED;

    public AccountState parse(String accountState) {
        return  Enum.valueOf(AccountState.class, accountState);
    }
}
