package com.example.RealConnect.property.domain.dto;

import com.example.RealConnect.property.domain.PropertyStatus;
import lombok.Builder;
import lombok.Getter;

// property 조회
@Getter
@Builder
public class PropertyResponseDto {

    private Long id;

    // 아파트
    private String apartmentName;
    private String apartmentAddress;

    // 중개사
    private Long agentId;
    private String agentName;
    private String agentEmail;

    // 소유자
    private String ownerName;
    private String ownerPhone;

    // 임차인
    private String tenantName;
    private String tenantPhone;

    // 거래
    private boolean isSale;
    private Long salePrice;

    private boolean isJeonse;
    private Long jeonsePrice;

    private boolean isMonth;
    private Long deposit;
    private Long monthPrice;

    // 상태 및 메모
    private PropertyStatus status;
    private String memo;
}
