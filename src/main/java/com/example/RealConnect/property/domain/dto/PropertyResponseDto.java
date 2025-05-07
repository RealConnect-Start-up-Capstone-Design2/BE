package com.example.RealConnect.property.domain.dto;

import com.example.RealConnect.property.domain.PropertyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

// property 조회
@Getter
@Builder
public class PropertyResponseDto {
    @NotNull(message = "매물 고유 아이디 입력하세요.")
    private Long id;

    // 아파트
    @NotBlank(message = "아파트 이름 입력하세요.")
    private String apartmentName;
    @NotBlank(message = "아파트 주소 입력하세요.")
    private String apartmentAddress;

    // 중개사
    @NotNull(message = "중개사 아이디 입력하세요.")
    private Long agentId;
    @NotBlank(message = "중개사 이름을 입력하세요.")
    private String agentName;
    @NotBlank(message = "중개사 이메일 입력하세요.")
    private String agentEmail;

    // 소유자
    @NotBlank(message = "소유자 이름을 입력하세요.")
    private String ownerName;
    @NotBlank(message = "소유자 전화번호를 입력하세요.")
    private String ownerPhone;

    // 임차인
    @NotBlank(message = "임차인 이름을 입력하세요.")
    private String tenantName;
    @NotBlank(message = "임차인 전화번호를 입력하세요.")
    private String tenantPhone;

    // 거래
    @NotNull(message = "거래 상태를 입력하세요.")
    private boolean isSale;
    @NotNull(message = "거래 가격을 입력하세요.")
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
