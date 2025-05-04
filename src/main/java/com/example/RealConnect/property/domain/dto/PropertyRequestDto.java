package com.example.RealConnect.property.domain.dto;

import com.example.RealConnect.property.domain.PropertyStatus;
import lombok.Getter;
import lombok.Setter;

// property 등록 및 수정
@Getter
@Setter
public class PropertyRequestDto {
    // 아파트 id
    private Long apartmentId;

    // 중개사 id
    private Long agentId;

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
