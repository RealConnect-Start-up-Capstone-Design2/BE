package com.example.RealConnect.property.domain.dto;

import com.example.RealConnect.property.domain.PropertyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

// property 등록 및 수정
@Getter
@Setter
public class PropertyCreateRequestDto {
    // 아파트 id
    @NotNull(message = "아파트 아이디를 입력하세요.")
    private Long apartmentId;

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

    private Long salePrice;

    private Long jeonsePrice;

    private Long deposit;
    private Long monthPrice;

    // 상태 및 메모
    private PropertyStatus status;
    private String memo;
}
