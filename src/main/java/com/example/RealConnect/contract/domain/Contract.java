package com.example.RealConnect.contract.domain;

import com.example.RealConnect.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Contract {

    @Id
    @GeneratedValue
    private Long id; // 식별자

    private String apartment; // 단지명
    private String dong; // 동
    private String ho; // 호
    private String area; // 면적

    private String ownerName; // 소유주(매도인)
    private String tenantName; // 임차인(매수인)

    @Enumerated(EnumType.STRING)
    private ContractType type; // 매매/전세/월세

    private Long price; // 거래 금액
    private LocalDateTime contractDate; // 계약일
    private LocalDateTime expireDate; // 계약 만기일

    @Enumerated(EnumType.STRING)
    private ContractStatus status; //  거래상태: 진행중, 완료 등

    private boolean favorite; // 즐겨찾기

    @ManyToOne(fetch = FetchType.LAZY)
    private User agent; // 공인중개사
}
