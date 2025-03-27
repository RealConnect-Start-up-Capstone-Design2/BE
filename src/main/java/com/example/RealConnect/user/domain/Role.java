package com.example.RealConnect.user.domain;

import lombok.Getter;

@Getter
public enum Role {
    BASIC("ROLE_BASIC"),
    VIP("ROLE_VIP"),
    ADMIN("ROLE_ADMIN");

    private String value;

    Role(String value)
    {
        this.value = value;
    }
}
