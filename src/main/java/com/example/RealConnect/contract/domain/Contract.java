package com.example.RealConnect.contract.domain;

import com.example.RealConnect.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 식별자

    private String apartment; // 단지명
    private String dong; // 동
    private String ho; // 호
    private String area; // 면적

    private String ownerName; // 소유주(매도인)
    private String ownerPhone; // 소유주(매도인) 연락처

    private String tenantName; // 임차인(매수인)
    private String tenantPhone; // 임차인(매수인) 연락처

    @Enumerated(EnumType.STRING)
    private ContractType type; // 매매/전세/월세

    private String price; // 거래 금액 // 월세인 경우를 고려하여 String 형으로 변환 2025.5.26
    private LocalDate contractDate; // 계약일
    private LocalDate expireDate; // 계약 만기일

    @Enumerated(EnumType.STRING)
    private ContractStatus status; //  거래상태: 진행중, 완료 등

    private boolean favorite; // 즐겨찾기

    @ManyToOne(fetch = FetchType.LAZY)
    private User agent; // 공인중개사


    /**
     * User, ID를 제외한 모든 필드를 덮어씀
     * @param dto
     * @return Contract
     */
    public Contract update(ContractPostRequestDto dto)
    {
        apartment = dto.getApartment();
        dong = dto.getDong();
        ho = dto.getHo();
        area = dto.getArea();
        ownerName = dto.getOwnerName();
        ownerPhone = dto.getOwnerPhone();
        tenantName = dto.getTenantName();
        tenantPhone = dto.getTenantPhone();

        price = dto.getContractPrice();
        type = dto.getContractType();

        contractDate = dto.getContractDate();
        expireDate = dto.getDueDate();

        status = dto.getContractStatus();
        favorite = dto.isFavorite();

        return this;
    }
}
