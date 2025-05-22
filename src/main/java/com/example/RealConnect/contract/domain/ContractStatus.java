package com.example.RealConnect.contract.domain;

public enum ContractStatus {
    // 계약 완료(계약이 유효한 상태),
    ACTIVE,
    // 계약기간이 끝난 상태(만기일 초과)
    EXPIRED,
    // 조기해지된 계약
    TERMINATED
}
