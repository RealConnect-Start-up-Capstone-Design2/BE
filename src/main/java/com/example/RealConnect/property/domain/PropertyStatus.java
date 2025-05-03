package com.example.RealConnect.property.domain;

import lombok.Getter;

@Getter
public enum PropertyStatus {

    WAITING("대기중"),
    RESERVED("예약"),
    CONTRACTED("계약");

    private String status;

    PropertyStatus(String status)
    {
        this.status = status;
    }
}
