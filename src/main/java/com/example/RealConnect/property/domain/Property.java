package com.example.RealConnect.property.domain;

import com.example.RealConnect.apartment.domain.Apartment;
import com.example.RealConnect.property.domain.dto.PropertyCreateRequestDto;
import com.example.RealConnect.property.domain.dto.PropertyModifyRequestDto;
import com.example.RealConnect.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /*
        아파트 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Apartment apartment;

    /*
        중개사
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User agent;

    /*
        소유자
     */
    private String ownerName;
    private String ownerPhone;


    /*
        임차인
     */
    private String tenantName;
    private String tenantPhone;

    /*
        매매가
     */
    private Long salePrice;

    /*
        전세가
     */
    private Long jeonsePrice;

    /*
        보증금(월세인 경우)
     */
    private Long deposit;

    /*
        월세
     */
    private Long monthPrice;

    /*
        상태
     */
    @Enumerated(EnumType.STRING)
    private PropertyStatus status;

    private String memo;

    // Entity 수정
    public void update(PropertyModifyRequestDto dto) {
        this.ownerName = dto.getOwnerName();
        this.ownerPhone = dto.getOwnerPhone();
        this.tenantName = dto.getTenantName();
        this.tenantPhone = dto.getTenantPhone();
        this.salePrice = dto.getSalePrice();
        this.jeonsePrice = dto.getJeonsePrice();
        this.deposit = dto.getDeposit();
        this.monthPrice = dto.getMonthPrice();
        this.memo = dto.getMemo();
    }

    // Status 수정
    public void changeStatus(PropertyStatus status) {
        this.status = status;
    }
}
