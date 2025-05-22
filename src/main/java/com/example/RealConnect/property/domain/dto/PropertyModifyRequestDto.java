package com.example.RealConnect.property.domain.dto;

import com.example.RealConnect.property.domain.PropertyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

// property 등록 및 수정
@Getter
@Setter
public class PropertyModifyRequestDto {


    private String ownerName;

    private String ownerPhone;


    private String tenantName;
    private String tenantPhone;

    private Long salePrice;

    private Long jeonsePrice;

    private Long deposit;
    private Long monthPrice;

    // 등록일, 만기일
    private LocalDate startDate;
    private LocalDate endDate;

    private PropertyStatus status;
    private String memo;
}
