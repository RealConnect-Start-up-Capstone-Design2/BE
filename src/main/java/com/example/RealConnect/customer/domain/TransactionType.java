package com.example.RealConnect.customer.domain;

import lombok.Getter;

/**
 * 거래유형
 */
@Getter
public enum TransactionType {

    SALE("매매"),
    JEONSE("전세"),
    MONTHLYRENT("월세");

    private String type;

    TransactionType(String type)
    {
        this.type = type;
    }
}
