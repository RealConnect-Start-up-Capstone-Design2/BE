package com.example.RealConnect.property.domain.dto;

import com.example.RealConnect.property.domain.Property;
import com.example.RealConnect.property.domain.PropertyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

// property 조회
@Getter
@Builder
public class PropertyResponseDto {
    private Long id;

    // 아파트
    private String apartmentName;

    // 소유자
    private String ownerName;
    private String ownerPhone;

    // 임차인
    private String tenantName;
    private String tenantPhone;

    private Long salePrice;

    private boolean isJeonse;
    private Long jeonsePrice;

    private boolean isMonth;
    private Long deposit;
    private Long monthPrice;

    // 상태 및 메모
    private PropertyStatus status;
    private String memo;

    public PropertyResponseDto(Property property) {
        this.id = property.getId();
        this.apartmentName = property.getApartment().getName();
        this.ownerName = property.getOwnerName();
        this.ownerPhone = property.getOwnerPhone();
        this.tenantName = property.getTenantName();
        this.tenantPhone = property.getTenantPhone();
        this.salePrice = property.getSalePrice();
        this.isJeonse = property.isJeonse();
        this.jeonsePrice = property.getJeonsePrice();
        this.isMonth = property.isMonth();
        this.deposit = property.getDeposit();
        this.monthPrice = property.getMonthPrice();
        this.status = property.getStatus();
        this.memo = property.getMemo();
    }
}
