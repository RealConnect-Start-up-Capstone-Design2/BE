package com.example.RealConnect.contract.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class ContractResponseDto {
    // 계약 조회 시 사용
    private Long id;
    private String apartment; // property 엔티티에서 가져옴
    private Integer dong; // property 엔티티에서 가져옴
    private Integer ho; // property 엔티티에서 가져옴
    private double area; // property 엔티티에서 가져옴

    private String ownerName; // Property.owner로 매핑
    private String tenantName;

    private ContractType contractType;
    private Long contractPrice;

    private LocalDate contractDate;
    private LocalDate dueDate;

    private ContractStatus contractStatus;
    private boolean favorite;
}
