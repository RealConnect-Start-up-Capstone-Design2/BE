package com.example.RealConnect.user.domain;

import lombok.Getter;

@Getter
public enum Role {
    BASIC("ROLE_BASIC"),
    VIP("ROLE_VIP");

    private String value;

    Role(String value)
    {
        this.value = value;
    }
}
